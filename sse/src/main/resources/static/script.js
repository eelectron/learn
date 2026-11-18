console.log("This is script : page loaded")

function openConnection() {
    const eventSource = new EventSource('/api/v1/stream')

    eventSource.onmessage = (event) => {
        console.log("Received message from " + JSON.stringify(event.data))
    }
}

const button = document.getElementById("click-me");
button.addEventListener("click", openConnection);