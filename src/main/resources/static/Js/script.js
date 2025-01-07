console.log("Script loaded");

const themeButton = document.getElementById("theme_change_button");
const html = document.querySelector("html");

// Get theme from local storage
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

// Set theme to local storage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

// Change current page theme
function changePageTheme(theme, oldTheme) {
    // Update local storage
    setTheme(theme);

    // Remove the old theme
    if (oldTheme) {
        html.classList.remove(oldTheme);
    }

    // Set the new theme
    if (theme === "dark") {
        html.classList.add("dark");
    } else {
        html.classList.remove("dark");
    }

    // Change the text of the button
    document
        .querySelector("#theme_change_button")
        .querySelector("span").textContent = theme === "light" ? "Dark" : "Light";
}

// Initial theme setup
let currentTheme = getTheme();
changePageTheme(currentTheme, "");

// Set the listener to change theme button
const changeThemeButton = document.querySelector("#theme_change_button");
changeThemeButton.addEventListener("click", (event) => {
    let oldTheme = currentTheme;
    console.log("Change theme button clicked");

    // Toggle theme
    currentTheme = currentTheme === "dark" ? "light" : "dark";
    console.log(currentTheme);

    // Change the page theme
    changePageTheme(currentTheme, oldTheme);
});