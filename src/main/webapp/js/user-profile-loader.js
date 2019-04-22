function fetchUserProfile() {
  const url = '/username.html?user=' +parameterUsername;
   fetch(url)
       .then((response) => {
       return response.text();
       })
       .then(profile) => {
       const profileForm = document.getElementById('profile-form');
       profileForm.action = profile;
       if(profileForm == ''){
             profile = 'Set Up Your Profile.';
           }

           profileForm.innerHTML = profileDiv;

       }

        console.log(user);
}


function confirmLoggedIn() {
fetch('/login-status')
        .then((response) => {
          return response.text();
        })
        .then((loginStatus) => {
          if (loginStatus.isLoggedIn) {
            fetchUserProfile( parameterUsername );
          }
          else {
            window.location.replace('/');
          }
        });
}

function fetchAboutMe( parameterUsername){
  const url = '/about?user=' + parameterUsername;
  fetch(url).then((response) => {
    return response.text();
  }).then((aboutMe) => {
    const aboutMeContainer = document.getElementById('about-me-container');
    if(aboutMe == ''){
      aboutMe = 'This user has not entered any information yet.';
    }

    aboutMeContainer.innerHTML = aboutMe;

  });
}

function buildUI() {
  confirmLoggedIn();
  fetchUserProfile();
  fetchAboutMe( parameterUsername)
}