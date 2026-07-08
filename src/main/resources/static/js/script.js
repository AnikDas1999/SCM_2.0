console.log("Script loaded");

let currentTheme = getTheme();

// Handle all core structural bindings on layout ready
document.addEventListener("DOMContentLoaded", () => {
    changeTheme();
    initSidebarDrawer(); // Initializing the responsive push logic
});

function changeTheme() {
    changePageTheme(currentTheme, currentTheme);
    
    const changeThemeButton = document.querySelector("#theme_change_button");

    if (changeThemeButton) {
        changeThemeButton.addEventListener("click", (event) => {
            console.log("change theme button clicked");
            let oldTheme = currentTheme;
            
            if (currentTheme === "dark") {
                currentTheme = "light";
            } else {
                currentTheme = "dark";
            }
            
            changePageTheme(currentTheme, oldTheme);
        });
    }
}

function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}

function changePageTheme(theme, oldTheme) {
    setTheme(theme);
    const htmlElement = document.querySelector("html");
    htmlElement.classList.remove(oldTheme);
    htmlElement.classList.add(theme);
    
    const buttonSpan = document.querySelector("#theme_change_button span");
    if (buttonSpan) {
        buttonSpan.textContent = theme === "light" ? "Dark" : "Light";
    }
}

// Responsive push layout tracking logic
function initSidebarDrawer() {
    const sidebarToggleBtn = document.querySelector('[data-drawer-toggle="logo-sidebar"]');
    const sidebarElement = document.getElementById("logo-sidebar");
    const mainContentElement = document.getElementById("main-content");

    if (sidebarToggleBtn && sidebarElement && mainContentElement) {
        sidebarToggleBtn.addEventListener("click", (e) => {
            e.stopPropagation();
            
            // Toggle the sidebar layout frame positioning state
            sidebarElement.classList.toggle("-translate-x-full");
            
            // Toggles padding-start smoothly to shift your content container over 
            mainContentElement.classList.toggle("md:ps-64");
        });

        // Closes the dynamic sidebar view wrapper if clicking outside active links
        document.addEventListener("click", (e) => {
            if (!sidebarElement.contains(e.target) && !sidebarToggleBtn.contains(e.target)) {
                sidebarElement.classList.add("-translate-x-full");
                
                // Safely clears the responsive padding rule on an outside click
                mainContentElement.classList.remove("md:ps-64");
            }
        });
    }
}