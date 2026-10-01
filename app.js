// ===============================
// LOGIN
// ===============================

const loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", function (e) {

        e.preventDefault();

        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value;

        const selectedRole =
            document.getElementById("role").value;


        if (email === "" || password === "") {

            alert("Please enter email and password");

            return;
        }


        fetch("http://localhost:8080/login", {

            method: "POST",

            headers: {
                "Content-Type":
                    "application/x-www-form-urlencoded"
            },

            body: new URLSearchParams({

                email: email,
                password: password

            })

        })


        .then(response => response.text())


        .then(result => {

            console.log("Login result:", result);


            if (
                result !== "invalid" &&
                result !== "error" &&
                result.includes("|")
            ) {

                const userData =
                    result.split("|");


                const userName =
                    userData[0];

                const userRole =
                    userData[1];


                // Save login information

                localStorage.setItem(
                    "isLoggedIn",
                    "true"
                );

                localStorage.setItem(
                    "registeredName",
                    userName
                );

                localStorage.setItem(
                    "registeredEmail",
                    email
                );

                localStorage.setItem(
                    "userRole",
                    userRole
                );


                // Redirect according to role

                if (userRole === "Admin") {

                    window.location.href =
                        "admin-dashboard.html";

                }

                else if (userRole === "Organizer") {

                    window.location.href =
                        "organizer-dashboard.html";

                }

                else if (userRole === "Author") {

                    window.location.href =
                        "author-dashboard.html";

                }

                else if (userRole === "Reviewer") {

                    window.location.href =
                        "reviewer-dashboard.html";

                }

                else if (userRole === "Participant") {

                    window.location.href =
                        "participant-dashboard.html";

                }

                else {

                    alert(
                        "Unknown user role: " +
                        userRole
                    );

                }

            }

            else {

                alert(
                    "Invalid email or password"
                );

            }

        })


        .catch(error => {

            console.error(error);

            alert(
                "Cannot connect to the Java server"
            );

        });

    });

}


// ===============================
// REGISTRATION
// ===============================

const registerForm =
    document.getElementById("registerForm");


if (registerForm) {

    registerForm.addEventListener(
        "submit",
        function (e) {

            e.preventDefault();


            const name =
                document.getElementById("name")
                .value.trim();


            const email =
                document.getElementById("regEmail")
                .value.trim();


            const password =
                document.getElementById("regPassword")
                .value;


            const role =
                document.getElementById("regRole")
                .value;


            // Check empty fields

            if (
                name === "" ||
                email === "" ||
                password === ""
            ) {

                alert(
                    "Please fill all fields"
                );

                return;
            }


            // Send registration data to Java

            fetch("http://localhost:8084/register", {

                method: "POST",

                headers: {
                    "Content-Type":
                        "application/x-www-form-urlencoded"
                },

                body: new URLSearchParams({

                    name: name,

                    email: email,

                    password: password,

                    role: role

                })

            })


            .then(response => response.text())


            .then(result => {

                console.log(
                    "Registration result:",
                    result
                );


                if (result === "success") {

                    alert(
                        "Registration successful!"
                    );


                    // Go to login

                    window.location.href =
                        "login.html";

                }

                else {

                    alert(
                        "Registration failed. Email may already exist."
                    );

                }

            })


            .catch(error => {

                console.error(error);

                alert(
                    "Cannot connect to the Java registration server"
                );

            });

        }
    );

}


// ===============================
// AUTHENTICATION
// ===============================

if (

    window.location.pathname.includes(
        "admin-dashboard.html"
    )

    ||

    window.location.pathname.includes(
        "organizer-dashboard.html"
    )

    ||

    window.location.pathname.includes(
        "author-dashboard.html"
    )

    ||

    window.location.pathname.includes(
        "reviewer-dashboard.html"
    )

    ||

    window.location.pathname.includes(
        "participant-dashboard.html"
    )

) {

    const loggedIn =
        localStorage.getItem(
            "isLoggedIn"
        );


    // If not logged in, go to login

    if (loggedIn !== "true") {

        window.location.href =
            "login.html";

    }


    // Display current role

    const role =
        localStorage.getItem(
            "userRole"
        );


    const userRole =
        document.getElementById(
            "userRole"
        );


    if (userRole) {

        userRole.textContent =
            role;

    }

}


// ===============================
// LOGOUT
// ===============================

function logout() {

    localStorage.removeItem(
        "isLoggedIn"
    );

    localStorage.removeItem(
        "userRole"
    );

    localStorage.removeItem(
        "registeredName"
    );

    localStorage.removeItem(
        "registeredEmail"
    );


    window.location.href =
        "login.html";
}