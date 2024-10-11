const token = $("meta[name='_csrf']").attr("content");
const header = $("meta[name='_csrf_header']").attr("content");
const reportOfWorkoutId = $("meta[name='report-of-workout-plan-id']").attr("content");
let saveButton = document.getElementById('save-btn')
let form = document.getElementById('report-from');
let previousInput = null;
let userId, date;
document.addEventListener('DOMContentLoaded', function() {
    fetch(`/user/getReportOfWorkoutById/${reportOfWorkoutId}`)
        .then(response => response.json())
        .then(data => {
            userId = data.userId;
            date = data.date;
            data.exercises.forEach((exercise, index) => {
                // Создаем новый элемент input
                let input = document.createElement('input');
                let div = document.createElement('div');
                let i = document.createElement('i');
                div.className = 'inputBox'
                // Устанавливаем тип и значение для input
                input.type = 'text';
                input.name = 'exercise' + index
                input.value = data.reports[index]

                i.textContent = exercise

                div.appendChild(input)
                div.appendChild(i)

                form.insertBefore(div, saveButton);

                if (previousInput) {
                    previousInput.focus();
                }
                previousInput = input;
            });
        })
        .catch(error => console.error('Error:', error));
});
saveButton.addEventListener('click', function() {
    const formData = {};
    const inputs = document.querySelectorAll('.form input');
    const i = document.querySelectorAll('.form i')

    let exercisesList = [];
    let reportsList = [];
    i.forEach(function(ii) {
        exercisesList.push(ii.textContent);
    });
    inputs.forEach(function(input) {
        // Проверяем имя input и добавляем данные в соответствующий список
        if (input.name.includes('exercise')) {
            reportsList.push(input.value);
        }
    });
    formData['exercises'] = exercisesList;
    formData['reports'] = reportsList;
    formData['date'] = date;
    formData['userId'] = userId;


    fetch(`/user/updateReportOfWorkout/${reportOfWorkoutId}`, {
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