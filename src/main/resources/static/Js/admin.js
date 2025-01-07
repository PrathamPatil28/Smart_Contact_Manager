console.log("admin.js loaded");

// Handle file input change and update image preview
document
  .querySelector("#image_file_input")
  .addEventListener("change", function (event) {
    let file = event.target.files[0];
    let reader = new FileReader();
    reader.onload = function () {
      document
        .querySelector("#upload_image_preview")
        .setAttribute("src", reader.result);
    };
    reader.readAsDataURL(file);
  });

// Handle file input change and update image preview
// Clear the image preview on form reset
document
  .querySelector("form")
  .addEventListener("reset", function () {
    // Clear the image preview
    document.querySelector("#upload_image_preview").setAttribute("src", "");
  });

console.log("this is the admin page");

const imageInput = document.getElementById('image_file_input');
const imagePreview = document.getElementById('upload_image_preview');

if(imageInput){
    imageInput.addEventListener('change', function () {
      if(imageInput.files.length <= 0 ) return;
      const [image] = imageInput.files;
      if (image) {
        imagePreview.src = URL.createObjectURL(image);
      }
    });
}

const profileImageInput = document.getElementById('image_file_input_profile');
const profileImagePreview = document.getElementById('upload_profile_preview');

if(profileImageInput){
    profileImageInput.addEventListener('change', function () {
      if(profileImageInput.files.length <= 0 ) return;
      const [image] = profileImageInput.files;
      if (image) {
        profileImagePreview.src = URL.createObjectURL(image);
      }
    });
}