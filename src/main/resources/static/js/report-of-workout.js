const tableContainer = document.querySelector('.table-container');
const table = document.createElement('table');
const thead = document.createElement('thead');
const tbody = document.createElement('tbody');

let authId;
const token = $("meta[name='_csrf']").attr("content");

fetch('/user/getAuthUser')
    .then(response => response.json())
    .then(data => {
        authId = data.id;
        if (data.role === 'ROLE_USER'){
            const theadRow = thead.insertRow();
            theadRow.innerHTML =
                '            <th>Дата тренировки</th>'+
                '            <th>Упражнения</th>'+
                '            <th>Отчет</th>'+
                '            <th>Редактировать отчет</th>'
            ;
            table.appendChild(thead);
            tableContainer.appendChild(table);
            getReportOfWorkoutForUser();
        }
        else {
            const userId = $("meta[name='user-id']").attr("content");

            updateUserLinks('.main-user', 'getUserPage', userId);
            updateUserLinks('.workout-user', 'getWorkoutPlanPageByUserId', userId);
            updateUserLinks('.reports-user', 'getReportOfWorkoutPageByUserId', userId);
            updateUserLinks('.nutrition-user', 'getNutritionReportPageByUser', userId);

            const theadRow = thead.insertRow();
            theadRow.innerHTML =
                '            <th>Дата тренировки</th>'+
                '            <th>Упражнения</th>'+
                '            <th>Отчет</th>'
            ;
            table.appendChild(thead);
            tableContainer.appendChild(table);
            getReportOfWorkoutForCoach(userId);
        }
    });

function getReportOfWorkoutForUser() {
    fetch(`/user/getReportOfWorkoutByUser/${authId}`)
        .then(response => response.json())
        .then(data => {
            data.forEach(reportOfWorkout => {
                const row = tbody.insertRow();
                row.setAttribute('data-report-of-workout', JSON.stringify(reportOfWorkout));

                const reportDate = new Date(reportOfWorkout.date);
                const formattedDate = reportDate.toLocaleDateString('en-GB');

                row.innerHTML = `
                                <td>${formattedDate}</td>
                                <td><ul>${reportOfWorkout.exercises.map(exercise => `<li>${exercise}</li>`).join('')}</ul></td>
                                <td><ul>${reportOfWorkout.reports.map(repetition => `<li>${repetition}</li>`).join('')}</ul></td>
                                <td><button class="table-btn" id="edit-report-btn">Изменить</button></td>
                               `
                ;
                const btnCreateReport = row.querySelector('#edit-report-btn')
                btnCreateReport.addEventListener('click', ()=> {
                    window.location.href = `/user/getEditReportOfWorkoutForm/${reportOfWorkout.id}`;
                });
                table.appendChild(tbody);
                tableContainer.appendChild(table);
            })
        });
}

function getReportOfWorkoutForCoach(userId){
    fetch(`/user/getReportOfWorkoutByUser/${userId}`)
        .then(response => response.json())
        .then(data => {
            data.forEach(reportOfWorkout => {
                const row = tbody.insertRow();
                row.setAttribute('data-report-of-workout', JSON.stringify(reportOfWorkout));
                row.innerHTML = `
                                <td>${reportOfWorkout.date}</td>
                                <td><ul>${reportOfWorkout.exercises.map(exercise => `<li>${exercise}</li>`).join('')}</ul></td>
                                <td><ul>${reportOfWorkout.reports.map(repetition => `<li>${repetition}</li>`).join('')}</ul></td>
                               `
                ;
                table.appendChild(tbody);
                tableContainer.appendChild(table);
            })
        });
}

function updateUserLinks(selector, path, userId) {
    const elements = document.querySelectorAll(selector);
    for (const elem of elements) {
        elem.href = `/user/${path}/${userId}`;
    }
}