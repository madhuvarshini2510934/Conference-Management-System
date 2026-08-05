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

        window.location.href = "dashboard.html";
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

        window.location.href = "login.html";
    });
}

// AUTHENTICATION

if (window.location.pathname.includes("dashboard.html")) {
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