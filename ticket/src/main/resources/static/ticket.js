function getTickets(eventId) {
    const url = '/ticket-type?eventId=' + eventId;
    return fetch(url).then(r => r.json())
}

function renderTickets(tickets) {
    let html = '';

    for(let i=0; i<tickets.length; i++) {
        const ticket = tickets[i];

        if (i % 4 === 0) {
            html += `<div class="w3-row-padding w3-padding-16 w3-center">\n`;
        }

        html += `
<div class="w3-quarter">
    <img src="/images/t${(i % 4) + 1}.jpg" style="width:100%">
    <h3>${ticket.title}</h3>
    <p>€ ${ticket.price}</p>
    <p>
        <a href="javascript:showDialog(${ticket.ticketTypeId})">Buy Now!</a>
    </p>
</div>
`

        if (i % 4 === 3) {
            html += `</div>\n`;
        }

    }

    document.getElementById("main").innerHTML = html;

}

let selectedTicketTypeId;

function showDialog(ticketTypeId) {
    selectedTicketTypeId = ticketTypeId;
    document.getElementById('dialog').hidden = false;
}

function book() {
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;

    if (!firstName || !lastName) {
        document.getElementById('error').style.visibility = 'visible';
        return false;
    }

    const ticket = {
        purchaseDate: new Date().toISOString(),
        ticketTypeId: selectedTicketTypeId,
        holder: {
            firstName,
            lastName
        }
    };

    fetch('/ticket', {
        body: JSON.stringify(ticket),
        method: 'POST',
        headers: {
            "content-type": "application/json"
        }
    })
    .then(_ => {
        close();
        showConfirm();
    })
    .catch(_ => {
        alert("Unexpected Error!");
        close();

    });

    return false;
}

function close() {
    document.getElementById('dialog').hidden = true;
}

function showConfirm() {
    document.getElementById('confirm').hidden = false;
}

function closeConfirm() {
    document.getElementById('confirm').hidden = true;
}

function getEventId() {
    const match = location.href.match(/eventId=(\d+)/);
    if (!match) return null;
    return match[1];
}

function main() {

    close();
    closeConfirm();

    document.getElementById("close").addEventListener('click', close);
    document.getElementById("closeConfirm").addEventListener('click', closeConfirm);
    document.getElementById("book").addEventListener('click', book);

    const eventId = getEventId();
    getTickets(eventId).then(tickets => renderTickets(tickets));
}

main();