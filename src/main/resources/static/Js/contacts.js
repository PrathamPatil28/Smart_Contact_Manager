console.log("Contacts script loaded");
const baseURL = "http://localhost:8080";

const viewContactModal = document.getElementById("view_contact_modal");

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
  id: 'view_contact_modal',
  override: true
};

// Ensure the correct modal library is used
const contactModal = new Modal(viewContactModal, options);

function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactdata(id) {
    try {
        console.log("Loading contact data for id: " + id);
        const data = await (await fetch(`${baseURL}/api/contact/${id}`)).json();
        console.log(data);

        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_image").src = data.picture;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_phone").innerHTML = data.phoneNumber;
        document.querySelector("#contact_about").innerHTML = data.description;
        const contactFavorite = document.querySelector("#contact_favourite");

        if (data.favourite) {
            contactFavorite.innerHTML = 
            "<i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i><i class='fas fa-star text-yellow-400'></i>";
        } else {
            contactFavorite.innerHTML = "Not Favorite Contact";
        }

        document.querySelector("#contact_website").href = data.webSiteLink;
        document.querySelector("#contact_website").innerHTML = data.webSiteLink;
        document.querySelector("#contact_linkedIn").href = data.linkedinLink;
        document.querySelector("#contact_linkedIn").innerHTML = data.linkedinLink;
        openContactModal();
    } catch (error) {
        console.log("Error loading contact data: " + error);
    }
}

async function deleteContact(id) {
    console.log("Deleting contact with id: " + id);

    Swal.fire({
        title: "Do you want to delete this Contact?",
        showCancelButton: true,
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!",
        icon: "warning",
        confirmButtonColor: "#3085d6",
    }).then(async (result) => {
        if (result.isConfirmed) {
            try {
                const response = await fetch(`${baseURL}/user/contact/delete/${id}`, {
                    method: 'GET'
                });
                if (response.ok) {
                    Swal.fire("Deleted!", "", "contact deleted successfully");
                    window.location.replace(`${baseURL}/user/contact`);
                } else {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
            } catch (error) {
                console.error("Error deleting contact: ", error);
                Swal.fire("Something went wrong", "", "error");
            }
        }
    });
}