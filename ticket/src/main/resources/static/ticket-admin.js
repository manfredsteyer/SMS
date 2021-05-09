function getTickets(eventId) {
    const url = '/ticket-type?eventId=' + eventId;
    return fetch(url).then(r => r.json())
}

function renderTickets(tickets) {
     let html = `
<tr>
    <th>Title</th>
    <th>Price</th>
    <td></td>
</tr>
`;

    for(let i=0; i<tickets.length; i++) {
        const ticket = tickets[i];

        html += `
<tr>
    <td>${ticket.title}</td>
    <td>${ticket.price}</td>
    <td>
        <a href="javaScript:deleteTicket(${ticket.ticketTypeId})">Delete</a>
    </td>
</tr>
`
    }

    document.getElementById("mainTable").innerHTML = html;
}

function deleteTicket(id) {
    const url = '/ticket-type/' + id;
    fetch(url, {
        method: 'DELETE'
    })
    .then(_ => {
        showTickets();
    })
    .catch(err => {
        console.error('err', err);
        alert('Unexpected Error!');
    })
}

function createTicket() {
    const url = '/ticket-type';

    const title = document.getElementById('title').value;
    const price = parseFloat(document.getElementById('price').value);

    if (!title || !price) {
        alert('Title and price are required and price must be a number!');
        return;
    }

    const ticket = {
        title, 
        price,
        eventId: getEventId()
    }

    fetch(url, {
        method: 'POST',
        body: JSON.stringify(ticket),
        headers: {
            "content-type": "application/json"
        }
    })
    .then(_ => {
        showTickets();
    })
    .catch(err => {
        console.error('err', err);
        alert('Unexpected Error!');
    })
}

function showTickets() {
    const eventId = getEventId();
    getTickets(eventId).then(tickets => renderTickets(tickets));
}



function getEventId() {
    const match = location.href.match(/eventId=(\d+)/);
    if (!match) return null;
    return match[1];
}

function main() {
    document.getElementById('btnSave').addEventListener('click', createTicket);
    showTickets();
}

main();