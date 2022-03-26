// Logout Functionality
let logoutBtn = document.querySelector("#logout-btn");

logoutBtn.addEventListener('click', () => {
    localStorage.removeItem('user_role_id');
    localStorage.removeItem('user_id');
    localStorage.removeItem('jwt');

    window.location = '../index.html';
});

let welcomeText = document.querySelector('#welcome-tag');
welcomeText.innerText = `Welcome back!`;

window.addEventListener('load', (event) => {
    populateReimbursementsTable();
});

async function populateReimbursementsTable() {
    const URL = 'http://localhost:8081/reimbursements';

    let res = await fetch(URL, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('jwt')}` // Send over the JWT
        }

    });

    if (res.status === 200) {
        let reimbursements = await res.json();

        let tbody = document.querySelector('#reimbursement-tbl > tbody');
        tbody.innerHTML = ''; // Clear the body on reloads

        for (let ticket of reimbursements) {
            let tr = document.createElement('tr');

            let td1 = document.createElement('td');
            if (ticket.status === "Pending") {
                td1.innerText = "⏳";
            } else if (ticket.staus === "Approved") {
                td1.innerText = "✔";
                td1.setAttribute("color", "green");
            } else {
                td1.innerText = "🅧";
                td1.setAttribute("color", "red"); 
            }

            let td2 = document.createElement('td');
            td2.innerText = ticket.submitTimestamp;

            let td3 = document.createElement('td');
            td3.innerText = ticket.firstName;

            let td4 = document.createElement('td');
            td4.innerText = ticket.lastName;

            let td5 = document.createElement('td');
            td5.innerText = ticket.amount;

            let td6 = document.createElement('td');
            td6.innerText = ticket.type;

            let td7 = document.createElement('Description');
            td7.innerText = ticket.description;

            let td8 = document.createElement('td');
            let aUrl = document.createElement('a');
            aUrl.setAttribute("id", "detail-link");
            aUrl.setAttribute("href", "#");
            aUrl.innerText = "Details";

            td8.appendChild(aUrl);

            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            tr.appendChild(td7);
            tr.appendChild(td8);

            tbody.appendChild(tr);
                
        }
    }
}