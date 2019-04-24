  let editMarker;
    let map;
    let latitude;
    let longitude;
  
    function createMap(){

      map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 38.5949, lng: -94.8923},
        zoom: 4
      });
   
      map.addListener('click', (event) => {
        createMarkerForEdit(map, event.latLng.lat(), event.latLng.lng());
          latitude = event.latLng.lat();
          longitude = event.latLng.lng();
      });
      
     fetchMarkers();
    }
    
    function fetchMarkers(){
      fetch('/user-markers').then((response) => {
        return response.json();
      }).then((markers) => {
        markers.forEach((marker) => {
         createMarkerForDisplay(map, marker.lat, marker.lng, marker.content)
        });  
      });
    }
    
    function createMarkerForDisplay(map, lat, lng, content){

      const marker = new google.maps.Marker({
        position: {lat: lat, lng: lng},
        map: map
      });
              
      var infoWindow = new google.maps.InfoWindow({                             //could use this to display event info
        content: content
      });
      marker.addListener('click', () => {
        infoWindow.open(map, marker);
      });
    }
    
    function postMarker(lat, lng, content){
      const params = new URLSearchParams();
      params.append('lat', lat);
      params.append('lng', lng);
      params.append('content', content);
      
      fetch('/user-markers', {
        method: 'POST',
        body: params
      });
    }
    
  
    
//    function buildInfoWindowInput(lat, lng){
//      const textBox = document.createElement('textarea');
//      const button = document.createElement('button');
//      button.appendChild(document.createTextNode('Submit'));
//     
//      button.onclick = () => {
//        createMarkerForDisplay(map, lat, lng, textBox.value);
//        editMarker.setMap(null);
//        postMarker(lat, lng, textBox.value);
//      };
//     
//      const containerDiv = document.createElement('div');
//      containerDiv.appendChild(textBox);
//      containerDiv.appendChild(document.createElement('br'));
//      containerDiv.appendChild(button);
//     
//      return containerDiv;
//    }

 function createMarkerForEdit(map, lat, lng){

      if(editMarker){
       editMarker.setMap(null);
      }
     
      editMarker = new google.maps.Marker({
        position: {lat: lat, lng: lng},
        map: map
      });  
         
//      const infoWindow = new google.maps.InfoWindow({
//        content: buildInfoWindowInput(lat, lng)                               //silenced textbox output
//      });
//      
//      google.maps.event.addListener(infoWindow, 'closeclick', () => {
//        editMarker.setMap(null);
//      });
//      
//      infoWindow.open(map, editMarker);
    }

function submit(){          //if submit button is pressed, then this function is called and information is recorded
   
     

     
    
    let nameEvent;
    let timeEvent;
    let dateEvent;
    let purposeEvent;
   
    nameEvent = document.getElementById("nameEvent").value;
    timeEvent = document.getElementById("timeEvent").value;
    dateEvent = document.getElementById("dateEvent").value;
    purposeEventEvent = document.getElementById("purposeEvent").value;
    
    dateEvent = document.getElementById("dateEvent").value = longitude;
    document.getElementById("purposeEvent").value = latitude;
    


    
}
