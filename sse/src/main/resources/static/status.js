console.log("status.js file loaded");

const eventId = "sheep_detective";

// receiving sse message from server
const eventSource = new EventSource(`/api/v1/status/subscribe/${eventId}`);
eventSource.onmessage = (event) => {
    console.log(JSON.stringify(event));

    // show data on html page
    const message = document.getElementById("message");
    message.textContent = event.data;
}

// start process
async function receiveMessage(id) {
    const response = fetch(`/api/v1/status/start/${id}`, {
        method: "GET",
    })

    const data = await response.json();
    console.log(JSON.stringify(data));
}

const receiveSse = document.getElementById("receive-sse");
receiveSse.addEventListener("click", () => receiveMessage(eventId));