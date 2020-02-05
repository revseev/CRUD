
$(document).ready(async function (e) {
    let url = 'http://localhost:8080/api/all';
    let response = await fetch(url, {method: 'POST'});

    let json = await response.json(); // читаем ответ в формате JSON

    console.log(json);
});

