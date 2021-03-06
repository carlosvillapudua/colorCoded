/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.servlets;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import com.google.cloud.language.v1.Document;

import com.google.cloud.language.v1.Document.Type;

import com.google.cloud.language.v1.LanguageServiceClient;

import com.google.cloud.language.v1.Sentiment;


import java.io.IOException;
import java.util.List;
import java.util.Map;

/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with a JSON representation of {@link Message} data for a specific user. Responds with
   * an empty array if the user is not provided.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json");

    String user = request.getParameter("user");

    if (user == null || user.equals("")) {
      // Request is invalid, return empty array
      response.getWriter().println("[]");
      return;
    }

    List<Message> messages = datastore.getMessages(user);
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    //Line 65 causes some error, commented by Nicole Barra
    //String aboutMe = Jsoup.clean(request.getParameter("about-me"), Whitelist.none());

    response.getWriter().println(json);
  }

  /** Stores a new {@link Message}. */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {



    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      System.out.println( "MessageServlet::doPost() - User is not logged in..." );
      response.sendRedirect("/");
      return;
    }


    //Edited by Nicole for Direct Messages step 3
    String user = userService.getCurrentUser().getEmail();
    String text = Jsoup.clean(request.getParameter("text"), Whitelist.none());

    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(request);
    List<BlobKey> blobKeys = null;

    if ( !blobs.isEmpty() )
      blobKeys = blobs.get("image");

    String recipient = request.getParameter("recipient");
//    float sentimentScore = getSentimentScore(text);
    float sentimentScore = 0;


    String regex = "(https?://\\S+\\.(png|jpg))";
    String replacement = "<img src=\"$1\" />";
    String textWithImagesReplaced = text.replaceAll(regex, replacement);


    //System.out.println( "Image Text: " + textWithImagesReplaced );
    // Edited by Timi for Styled Text pt1

    String parsedContent = textWithImagesReplaced.replace("[b]", "<strong>").replace("[/b]", "</strong>");

    //System.out.println( "Parse Text for Bold: " + parsedContent );

    parsedContent = parsedContent.replace("[i]", "<i>").replace("[/i]", "</i>");

    //System.out.println( "Parse Text for Italics: " + parsedContent );

    parsedContent = parsedContent.replace("[u]", "<ins>").replace("[/u]", "</ins>");

    System.out.println("Parse Text for underline: " + parsedContent );

    parsedContent = parsedContent.replace("[s]", "<del>").replace("[/s]", "</del>");

    System.out.println("Parse Text for StrikeThrough: " + parsedContent );

    //make sure generated HTML is valid and all tags are closed
    String cleanedContent = Jsoup.clean(parsedContent, Whitelist.none().addTags("strong", "i", "ins", "del"));



    Message message = new Message(user, cleanedContent, recipient, sentimentScore);

  

    if(blobKeys != null && !blobKeys.isEmpty()) {
      BlobKey blobKey = blobKeys.get(0);
      System.out.println( "Blob Key: " + blobKey.getKeyString() );
      System.out.println( "Blob Key as string: " + blobKey.toString() );

      ImagesService imagesService = ImagesServiceFactory.getImagesService();
      if ( imagesService != null )
      {
        ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
        if ( options != null ) {
          try {
            String imageUrl = imagesService.getServingUrl(options);
            message.setImageUrl(imageUrl);
          }
          catch ( Exception e ) {
            System.out.println( "Exception Caught: " + e.getMessage() );
            e.printStackTrace();
          }
        }
      }
    }


    datastore.storeMessage(message);


    /*Just checking if the recipient is being received

    if(recipient!= ""){
      System.out.println("Recipient has been received");
    }
    */


    response.sendRedirect("/" );
  }

  // New function by Nicole Barra for SEntiment Analysis

  private float getSentimentScore(String text) throws IOException {

  Document doc = Document.newBuilder()

      .setContent(text).setType(Type.PLAIN_TEXT).build();


  LanguageServiceClient languageService = LanguageServiceClient.create();

  Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();

  languageService.close();


  return sentiment.getScore();

  }
}

