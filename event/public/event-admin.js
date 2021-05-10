function getEvents() {
    return fetch('/api/event').then(r => r.json())
}

function renderEvents(events) {
    let html = `
<tr>
    <th>Title</th>
    <th>Description</th>
    <td></td>
</tr>
`;

    for(let i=0; i<events.length; i++) {
        const event = events[i];

        html += `
<tr>
    <td>${event.title}</td>
    <td>${event.description}</td>
    <td>
        <a href="javaScript:deleteEvent(${event.id})">Delete</a> |
        <a href="http://localhost:8081/ticket-admin.html?eventId=${event.id}">Tickets</a>
    </td>
</tr>
`

    }

    document.getElementById("mainTable").innerHTML = html;

}

function deleteEvent(id) {
    const url = '/api/event/' + id;
    fetch(url, {
        method: 'DELETE'
    })
    .then(_ => {
        showEvents();
    })
    .catch(err => {
        console.error('err', err);
        alert('Unexpected Error!');
    })
}

function createEvent() {
    const url = '/api/event';

    const title = document.getElementById('title').value;
    const description = document.getElementById('description').value;

    if (!title || !description) {
        alert('Title and description are required!');
        return;
    }

    const event = {
        title, description
    }

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(event),
        headers: {
            "content-type": "application/json"
        }
    })
    .then(_ => {
        showEvents();
    })
    .catch(err => {
        console.error('err', err);
        alert('Unexpected Error!');
    })
}

function showEvents() {
    getEvents().then(events => renderEvents(events));
}

function main() {
    document.getElementById('btnSave').addEventListener('click', createEvent);
    showEvents();
}


main();