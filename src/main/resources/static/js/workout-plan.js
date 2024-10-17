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
        if (data.role === "ROLE_USER"){
            const theadRow = thead.insertRow();
            theadRow.innerHTML =
                '            <th>Отчет</th>'+
                '            <th>Название тренировки</th>'+
                '            <th>Упражнение</th>'+
                '            <th>Повторения</th>'
            ;
            table.appendChild(thead);
            tableContainer.appendChild(table);
            getWorkoutPlanForUser();
        }
        else {
            const addWorkoutPlanBtn = document.querySelector('#add-workout-plan-btn');
            const checkReportOfWorkoutBtn = document.querySelector('#check-report-of-workout-btn');
            const userId = $("meta[name='user-id']").attr("content");
            addWorkoutPlanBtn.addEventListener('click', () => {
                window.location.href = `/user/getWorkoutPlanForm/${userId}`;
            })
            checkReportOfWorkoutBtn.addEventListener('click', () => {
                window.location.href = `/user/getReportOfWorkoutPageByUserId/${userId}`;
            })
            updateUserLinks('.main-user', 'getUserPage', userId);
            updateUserLinks('.workout-user', 'getWorkoutPlanPageByUserId', userId);
            updateUserLinks('.reports-user', 'getReportOfWorkoutPageByUserId', userId);
            updateUserLinks('.nutrition-user', 'getNutritionReportPageByUser', userId);
            const theadRow = thead.insertRow();
            theadRow.innerHTML =
                '            <th>Удаление</th>'+
                '            <th>Название тренировки</th>'+
                '            <th>Упражнение</th>'+
                '            <th>Повторения</th>'
            ;
            table.appendChild(thead);
            tableContainer.appendChild(table);
            getWorkoutPlanForCoach(userId)
        }
    });


function getWorkoutPlanForCoach(userId){
    fetch(`/user/getWorkoutPlanByUser/${userId}`)
        .then(response => response.json())
        .then(data => {
            data.forEach(workoutPlan => {
                const row = tbody.insertRow();
                row.setAttribute('data-workout-plan', JSON.stringify(workoutPlan));
                row.innerHTML = `
                                <td><button class="table-btn" id="delete-workout-plan-btn">Удалить</button></td>
                                <td>${workoutPlan.name}</td>
                                <td><ul>${workoutPlan.exercises.map(exercise => `<li>${exercise}</li>`).join('')}</ul></td>
                                <td><ul>${workoutPlan.repetitions.map(repetition => `<li>${repetition}</li>`).join('')}</ul></td>
                               `
                ;
                const btnDeleteWorkoutPlan = row.querySelector('#delete-workout-plan-btn')
                btnDeleteWorkoutPlan.addEventListener('click', ()=>{
                    fetch(`/user/deleteWorkoutPlanById/${workoutPlan.id}`, {
                        method: 'DELETE',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': token
                        },
                    })
                        .then(response => {
                            if (response.ok){
                                console.log(response)
                                window.location.href = `/user/getWorkoutPlanPageByUserId/${userId}`;
                            }
                        })
                        .catch(error => {
                            console.error('Ошибка при отправке формы:', error);
                        });
                })
                table.appendChild(tbody);
                tableContainer.appendChild(table);
            })
        });
}

function getWorkoutPlanForUser(){
    fetch(`/user/getWorkoutPlanByUser/${authId}`)
        .then(response => response.json())
        .then(data => {
            data.forEach(workoutPlan => {
                const row = tbody.insertRow();
                row.setAttribute('data-workout-plan', JSON.stringify(workoutPlan));
                row.innerHTML = `
                                <td><button class="table-btn" id="create-report-btn">Создать отчет</button></td>
                                <td>${workoutPlan.name}</td>
                                <td><ul>${workoutPlan.exercises.map(exercise => `<li>${exercise}</li>`).join('')}</ul></td>
                                <td><ul>${workoutPlan.repetitions.map(repetition => `<li>${repetition}</li>`).join('')}</ul></td>
                               `
                ;
                const btnCreateReport = row.querySelector('#create-report-btn')
                btnCreateReport.addEventListener('click', ()=> {
                    window.location.href = `/user/getReportOfWorkoutForm/${workoutPlan.id}`;
                });
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