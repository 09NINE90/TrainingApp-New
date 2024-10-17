const tableContainer = document.querySelector('.table-container');
const table = document.createElement('table');
const thead = document.createElement('thead');
const tbody = document.createElement('tbody');

const startWeek = document.querySelector('#start-week')
const endWeek = document.querySelector('#end-week')
const caloriesWeek = document.querySelector('#calories-week')
const proteinsWeek = document.querySelector('#proteins-week')
const fatsWeek = document.querySelector('#fats-week')
const carbohydratesWeek = document.querySelector('#carbohydrates-week')


let authId;
const token = $("meta[name='_csrf']").attr("content");

fetch('/user/getAuthUser')
    .then(response => response.json())
    .then(data => {
        authId = data.id;
        const theadRow = thead.insertRow();
        theadRow.innerHTML =
            '            <th>Калорийность</th>'+
            '            <th>Белки (г)</th>'+
            '            <th>Жиры (г)</th>'+
            '            <th>Углеводы (г)</th>'+
            '            <th>Дата</th>'
        ;
        table.appendChild(thead);
        tableContainer.appendChild(table);
        if (data.role === "ROLE_USER"){
            const addNutritionReportBtn = document.querySelector('#add-nutrition-report-btn');
            addNutritionReportBtn.addEventListener('click', () => {
                const calories = document.querySelector('#calories')
                const proteins = document.querySelector('#proteins')
                const fats = document.querySelector('#fats')
                const carbohydrates = document.querySelector('#carbohydrates')
                const date = document.querySelector('#date')
                if (
                    calories.value &&
                    proteins.value &&
                    fats.value &&
                    carbohydrates.value &&
                    date.value
                ){
                    const requestData = {
                        calories: calories.value,
                        proteins: proteins.value,
                        fats: fats.value,
                        carbohydrates: carbohydrates.value,
                        date: date.value
                    };
                    fetch('/user/addNutritionReport', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': token
                        },
                        body: JSON.stringify(requestData),
                    })
                        .then((response) => {
                            console.log(response);
                            if (response.ok){
                                window.location.href = '/user/getNutritionReportPage';
                            }
                        })
                        .catch((error) => {
                            console.error('Ошибка при регистрации:', error);
                        });
                }
                else {
                    alert("Заполните все поля")
                }
            })
            getNutritionReportForUser();
            fetch(`/user/getLastWeekNutritionReportByUser/${authId}`)
                .then(response => response.json())
                .then(data => {
                    const date1 = new Date(data.weekStart);
                    const date2 = new Date(data.weekEnd);
                    const weekStartDate = date1.toLocaleDateString('en-GB');
                    const weekEndDate = date2.toLocaleDateString('en-GB');
                    startWeek.textContent = weekStartDate;
                    endWeek.textContent = weekEndDate;
                    caloriesWeek.textContent = data.sumCalories;
                    proteinsWeek.textContent = data.sumProteins;
                    fatsWeek.textContent = data.sumFats;
                    carbohydratesWeek.textContent = data.sumCarbohydrates;

                });
        }
        else {
            const mainUser = document.querySelectorAll('.main-user');
            const workoutUser = document.querySelectorAll('.workout-user');
            const nutritionUser = document.querySelectorAll('.nutrition-user');
            const reportsUser = document.querySelectorAll('.reports-user');
            const activityUser = document.querySelectorAll('.activity-user');
            const userId = $("meta[name='user-id']").attr("content");

            for (const elem of mainUser) {
                elem.href = `/user/getUserPage/${userId}`;
            }
            for (const elem of workoutUser) {
                elem.href = `/user/getWorkoutPlanPageByUserId/${userId}`;
            }
            for (const elem of reportsUser) {
                elem.href = `/user/getReportOfWorkoutPageByUserId/${userId}`;
            }
            for (const elem of nutritionUser) {
                elem.href = `/user/getNutritionReportPageByUser/${userId}`;
            }

            fetch(`/user/getLastWeekNutritionReportByUser/${userId}`)
                .then(response => response.json())
                .then(data => {
                    const date1 = new Date(data.weekStart);
                    const date2 = new Date(data.weekEnd);
                    const weekStartDate = date1.toLocaleDateString('en-GB');
                    const weekEndDate = date2.toLocaleDateString('en-GB');
                    startWeek.textContent = weekStartDate;
                    endWeek.textContent = weekEndDate;
                    caloriesWeek.textContent = data.sumCalories;
                    proteinsWeek.textContent = data.sumProteins;
                    fatsWeek.textContent = data.sumFats;
                    carbohydratesWeek.textContent = data.sumCarbohydrates;

                });
            getNutritionReportForCoach(userId)
        }
    });


function getNutritionReportForUser(){
    fetch(`/user/getNutritionReportByUser/${authId}`)
        .then(response => response.json())
        .then(data => {
            data.forEach(nutritionReport => {
                const row = tbody.insertRow();
                row.setAttribute('data-nutrition-report', JSON.stringify(nutritionReport));
                const reportDate = new Date(nutritionReport.date);
                const formattedDate = reportDate.toLocaleDateString('en-GB');

                row.innerHTML = `
                                <td>${nutritionReport.calories}</td>
                                <td>${nutritionReport.proteins}</td>
                                <td>${nutritionReport.fats}</td>
                                <td>${nutritionReport.carbohydrates}</td>
                                <td>${formattedDate}</td>
                               `
                ;
                table.appendChild(tbody);
                tableContainer.appendChild(table);
            })
        });
}

function getNutritionReportForCoach(userId){
    fetch(`/user/getNutritionReportByUser/${userId}`)
        .then(response => response.json())
        .then(data => {
            data.forEach(nutritionReport => {
                const row = tbody.insertRow();
                row.setAttribute('data-nutrition-report', JSON.stringify(nutritionReport));
                const reportDate = new Date(nutritionReport.date);
                const formattedDate = reportDate.toLocaleDateString('en-GB');

                row.innerHTML = `
                                <td>${nutritionReport.calories}</td>
                                <td>${nutritionReport.proteins}</td>
                                <td>${nutritionReport.fats}</td>
                                <td>${nutritionReport.carbohydrates}</td>
                                <td>${formattedDate}</td>
                               `
                ;
                table.appendChild(tbody);
                tableContainer.appendChild(table);
            })
        });
}