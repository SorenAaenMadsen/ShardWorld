// Fetch data from the Java backend
async function fetchData() {
    const response = await fetch('/api/data/graph');
    const data = await response.json();
    return data;
}

// Function to create a chart
async function createChart() {
    const data = await fetchData();

    // Transform the data for the chart
    const labels = data.map(item => item.label);
    const values = data.map(item => item.data);

    const ctx = document.getElementById('myChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',  // Example chart type
        data: {
            labels: labels,
            datasets: [{
                label: 'Resource Quantities',
                data: values,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            indexAxis: 'y', // Set to 'y' for horizontal bars
            responsive: false,  // Disable responsive resizing

            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}

// Initialize the chart on page load
createChart();
