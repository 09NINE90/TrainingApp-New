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
            const mainUser = document.querySelector('.main-user');
            const workoutUser = document.querySelector('.workout-user');
            const nutritionUser = document.querySelector('.nutrition-user');
            const reportsUser = document.querySelector('.reports-user');
            const activityUser = document.querySelector('.activity-user');
            const addWorkoutPlanBtn = document.querySelector('#add-workout-plan-btn');
            const checkReportOfWorkoutBtn = document.querySelector('#check-report-of-workout-btn');
            const userId = $("meta[name='user-id']").attr("content");
            mainUser.href = `/user/getUserPage/${userId}`;
            workoutUser.href = `/user/getWorkoutPlanPageByUserId/${userId}`;
            reportsUser.href = `/user/getReportOfWorkoutPageByUserId/${userId}`;
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
                row.innerHTML = `
                                <td>${reportOfWorkout.date}</td>
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