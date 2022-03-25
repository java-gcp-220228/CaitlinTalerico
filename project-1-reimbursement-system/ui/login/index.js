let loginBtn = document.querySelector('#login-btn');

loginBtn.addEventListener('click', async () => {
    // Grab UserInput for Username and Password
    let usernameInput = document.querySelector("#username");
    let passwordInput = document.querySelector("#password");

    // Connection to backend
    const URL = 'http://localhost:8081/login';


    // Convert Username/Password into a JSON object
    const jsonString = JSON.stringify({
        "username": usernameInput.value,
        "password": passwordInput.value
    });

    // Send post request to the backend to login
    let res = await fetch(URL, {
        method: 'POST',
        body: jsonString,
    });

    // Get the returned token (if succeeded) and store in local storage
    let token = res.headers.get('Token');
    localStorage.setItem('jwt', token);

    if (res.status === 200) { // An 'ok' response
        let user = await res.json();

        localStorage.setItem('user_id', user.id); // Keep track of the user's id
        localStorage.setItem('user_role', user.userRole.id); // Keep track of the user's role
    
        if (user.userRole.id < 300) {
            window.location.pathname = '../managers/manager-page.html';
        } else if (user.userRole.id >= 300) {
            window.location = '../employees/reimbursements-page.html';
        }
    } else { // Invalid Login Error
        let errorMsg = await res.text();
        console.log(errorMsg);

        let errorElement = document.querySelector('#error-msg');
        errorElement.innerText = errorMsg;
        errorElement.style.color = 'red';
    }
})