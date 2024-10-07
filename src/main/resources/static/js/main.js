const userEmailSpan = document.querySelector('#user-email')
const userFirstNameSpan = document.querySelector('#user-first-name')
const userLastNameSpan = document.querySelector('#user-last-name')
const tableContainer = document.querySelector('.table-container');
const table = document.createElement('table');
const thead = document.createElement('thead');
const tbody = document.createElement('tbody');

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
fetch('/user/getAuthUser')
    .then(response => response.json())
    .then(data => {
        userEmailSpan.innerHTML = data.email;
        userFirstNameSpan.innerHTML = data.firstName;
        userLastNameSpan.innerHTML = data.lastName;
        console.log(data);
    })
    .catch(error => console.error('Ошибка получения данных:', error));

