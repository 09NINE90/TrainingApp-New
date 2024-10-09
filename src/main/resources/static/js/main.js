const userEmailSpan = document.querySelector('#user-email')
const userFirstNameSpan = document.querySelector('#user-first-name')
const userLastNameSpan = document.querySelector('#user-last-name')
const userRole = document.querySelector('#user-role')
const tableContainer = document.querySelector('.table-container');
const table = document.createElement('table');
const thead = document.createElement('thead');
const tbody = document.createElement('tbody');

fetch('/user/getAuthUser')
    .then(response => response.json())
    .then(data => {
        userEmailSpan.textContent = data.email;
        userFirstNameSpan.textContent = data.firstName;
        userLastNameSpan.textContent = data.lastName;
        userRole.textContent = data.role;
        if (data.role === 'ROLE_USER'){
            const theadRow = thead.insertRow();
            theadRow.innerHTML =
                '            <th>Вес (кг)</th>\n'+
                '            <th>Обхват ноги (см)</th>\n'+
                '            <th>Обхват руки (см)</th>'+
                '            <th>Обхват бедер (см)</th>'+
                '            <th>Обхват талии (см)</th>\n'+
                '            <th>Обхват грудной клетки (см)</th>'+
                '            <th>Дата</th>'
            ;
            table.appendChild(thead);
            tableContainer.appendChild(table);
            getMyPhysicalParameters()
        }
        else {
            const theadRowUsers = thead.insertRow();
            theadRowUsers.innerHTML =
                '            <th>Фамилия Имя</th>\n'+
                '            <th>Email</th>'+
                '            <th>Телефон</th>'+
                '            <th>Информация</th>\n'
            ;
            table.appendChild(thead);
            tableContainer.appendChild(table);
            getUsers();
        }
    })
    .catch(error => console.error('Ошибка получения данных:', error));

function getMyPhysicalParameters() {
    fetch('/user/getMyPhysicalParameters')
        .then(response => response.json())
        .then(data => {
            data.forEach(parameters => {
                const row = tbody.insertRow();
                row.setAttribute('data-parameters', JSON.stringify(parameters));
                row.innerHTML = `
                                <td>${parameters.weight}</td>
                                <td>${parameters.armCircumference}</td>
                                <td>${parameters.legGirth}</td>
                                <td>${parameters.chestCircumference}</td>
                                <td>${parameters.hipCircumference}</td>
                                <td>${parameters.waistCircumference}</td>
                                <td>${parameters.date}</td>
                               `
                ;
                table.appendChild(tbody);
                tableContainer.appendChild(table);
            })
        })
        .catch(error => console.error('Ошибка получения данных:', error));
}

function getUsers() {
    fetch('/user/getUsers')
        .then(response => response.json())
        .then(data => {
            data.forEach(users => {
                const row = tbody.insertRow();
                row.setAttribute('data-user', JSON.stringify(users));
                row.innerHTML = `
                                <td>${users.name}</td>
                                <td>${users.email}</td>
                                <td>${users.phoneNumber}</td>
                                <td><button id="to-user-page">Перейти</button></td>
                               `
                ;
                const btnToUserPage = row.querySelector('#to-user-page')
                btnToUserPage.addEventListener('click', ()=>{
                    window.location.href = `/user/getUserPage/${users.id}`;
                })
                table.appendChild(tbody);
                tableContainer.appendChild(table);
            })
        })
        .catch(error => console.error('Ошибка получения данных:', error));
}



