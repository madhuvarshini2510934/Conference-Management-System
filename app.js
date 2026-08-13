// LOGIN

const loginForm = document.getElementById("loginForm");

if (loginForm) {
    loginForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const role = document.getElementById("role").value;

        if (email === "" || password === "") {
            alert("Please enter email and password");
            return;
        }

        const savedEmail = localStorage.getItem("registeredEmail");
        const savedPassword = localStorage.getItem("registeredPassword");

        if (email !== savedEmail || password !== savedPassword) {
            alert("Invalid email or password");
            return;
        }

        localStorage.setItem("isLoggedIn", "true");
        localStorage.setItem("userRole", role);

        // Redirect based on role
        if (role === "Admin") {
            window.location.href = "admin-dashboard.html";
        }
        else if (role === "Organizer") {
            window.location.href = "organizer-dashboard.html";
        }
        else if (role === "Author") {
            window.location.href = "author-dashboard.html";
        }
        else if (role === "Reviewer") {
            window.location.href = "reviewer-dashboard.html";
        }
        else if (role === "Participant") {
            window.location.href = "participant-dashboard.html";
        }
    });
}

// REGISTRATION

const registerForm = document.getElementById("registerForm");

if (registerForm) {
    registerForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const name = document.getElementById("name").value;
        const email = document.getElementById("regEmail").value;
        const password = document.getElementById("regPassword").value;
        const role = document.getElementById("regRole").value;

        if (name === "" || email === "" || password === "") {
            alert("Please fill all fields");
            return;
        }

        localStorage.setItem("registeredName", name);
        localStorage.setItem("registeredEmail", email);
        localStorage.setItem("registeredPassword", password);
        localStorage.setItem("registeredRole", role);

        alert("Registration successful!");

        // Go to login page
        window.location.href = "login.html";
    });
}

// AUTHENTICATION

if (
    window.location.pathname.includes("admin-dashboard.html") ||
    window.location.pathname.includes("organizer-dashboard.html") ||
    window.location.pathname.includes("author-dashboard.html") ||
    window.location.pathname.includes("reviewer-dashboard.html") ||
    window.location.pathname.includes("participant-dashboard.html")
) {

    const loggedIn = localStorage.getItem("isLoggedIn");

    if (loggedIn !== "true") {
        window.location.href = "login.html";
    }

    const role = localStorage.getItem("userRole");
    const userRole = document.getElementById("userRole");

    if (userRole) {
        userRole.textContent = role;
    }
}

// LOGOUT

function logout() {
    localStorage.removeItem("isLoggedIn");
    localStorage.removeItem("userRole");
    window.location.href = "login.html";
}