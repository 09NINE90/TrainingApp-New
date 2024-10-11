const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
const workoutPlanId = $("meta[name='workout-plan-id']").attr("content");
let saveReportButton = document.getElementById('save-report-dtn')
let form = document.getElementById('report-from');
let previousInput = null;
document.addEventListener('DOMContentLoaded', function() {
    let dateReport = document.querySelector('#date-report')
    const formattedDate = new Date().toISOString().slice(0, 10);
    dateReport.value = formattedDate;
    fetch('/user/getWorkoutPlanById/' + workoutPlanId)
        .then(response => response.json())
        .then(data => {
            data.exercises.forEach((exercise, index) => {
                // Создаем новый элемент input
                let input = document.createElement('input');
                let div = document.createElement('div');
                let i = document.createElement('i');
                div.className = 'inputBox'
                // Устанавливаем тип и значение для input
                input.type = 'text';
                input.name = 'exercise' + index

                i.textContent = exercise

                div.appendChild(input)
                div.appendChild(i)

                form.insertBefore(div, saveReportButton);

                if (previousInput) {
                    previousInput.focus();
                }
                previousInput = input;
            });
        })
        .catch(error => console.error('Error:', error));
});
saveReportButton.addEventListener('click', function() {
    const formData = {};
    const inputs = document.querySelectorAll('.form input');
    const i = document.querySelectorAll('.form i')

    let exercisesList = [];
    let reportsList = [];
    i.forEach(function(ii) {
        if (ii.textContent !== 'Дата'){
            exercisesList.push(ii.textContent);
        }
    });
    inputs.forEach(function(input) {
        // Проверяем имя input и добавляем данные в соответствующий список
        if (input.name.includes('exercise')) {
            if(input.value === ''){
                reportsList.push('Не сделано');
            }else {
                reportsList.push(input.value);
            }

        } else if (input.name.includes('date')) {
            formData[input.name] = input.value;
        }
    });
    formData['exercises'] = exercisesList;
    formData['reports'] = reportsList;

    fetch('/user/saveReportOfWorkout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': token
        },
        body: JSON.stringify(formData)
    })
        .then(response => {
            if (response.ok){
                console.log(response)
                window.location.href = '/user/getReportOfWorkoutPage'
            }
        })
        .catch(error => {
            console.error('Ошибка при отправке формы:', error);
        });
})