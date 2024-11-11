// Function to load content dynamically into the tab-content area
function loadTabContent(page, textContent) {
    // Fetch content from the corresponding HTML file
    console.log('loadTabContent function called for ' + page + ' textContent ' + textContent);
    fetch(page)
        .then(response => response.text())
        .then(html  => {

            // console.log('loadTabContent content ' + content);
            // Insert the fetched content into the tab-content div
            document.getElementById('tab-content').innerHTML = html ;
            if (page === 'resource-status.html') {
                fetchChartDataAndCreateChart();
            }
            // Activate the correct tab
            const tabs = document.querySelectorAll('.tab');
            console.log('loadTabContent tabs ' + tabs);
            tabs.forEach(tab => tab.classList.remove('active'));
            const activeTab = [...tabs].find(tab => tab.textContent.toLowerCase().includes(textContent.toLowerCase()));
            activeTab.classList.add('active');
        })
        .catch(error => console.error('Error loading tab content:', error));
}

// Initial load of the first tab (World Control)
window.onload = () => loadTabContent('world-control.html', 'World Control');

// Function to fetch data and create the chart
function fetchChartDataAndCreateChart() {
    fetch('http://localhost:8080/api/data/graph')
        .then(response => response.json())
        .then(data => {
            console.log('Data received:', data); // Debugging line
            createChart(data);  // Call createChart with fetched data
        })
        .catch(error => console.error('Error fetching data:', error));
}

// Function to create the chart (in resource-status tab)
function createChart(data) {
    const labels = data.map(item => item.label);
    const values = data.map(item => item.data);

    const ctx = document.getElementById('myChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
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


let currentDay = 0;
function advanceDay() {
    console.log('advanceDay');
    currentDay++;
    document.getElementById('world-status').textContent = `World is at day ${currentDay}.`;

    fetch('http://localhost:8080/api/advance-day', {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // console.log('Advance day response:', response.text());
            return response.text();
        })
        .then(data => {
            console.log('Advance day response:', data);
            //alert(data); // Optional: show an alert to confirm the action
        })
        .catch(error => {
            console.error('Error advancing day:', error);
            alert('Failed to advance day. Please try again.');
        });
}