function getEvents() {
    return fetch('/api/event').then(r => r.json())
}

function renderEvents(events) {
    let html = '';

    for(let i=0; i<events.length; i++) {
        const event = events[i];

        if (i % 4 === 0) {
            html += `<div class="w3-row-padding w3-padding-16 w3-center">\n`;
        }

        html += `
<div class="w3-quarter">
    <img src="/images/${(i % 7) + 1}.jpg" style="width:100%">
    <h3>${event.title}</h3>
    <p>${event.description}</p>
    <p>
        <a href="http://localhost:8081/index.html?eventId=${event.id}">Get Your Ticket!</a>
    </p>
</div>
`

        if (i % 4 === 3) {
            html += `</div>\n`;
        }

    }

    document.getElementById("main").innerHTML = html;

}

function main() {
    getEvents().then(events => renderEvents(events));
}

main();