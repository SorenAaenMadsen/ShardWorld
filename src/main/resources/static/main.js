// Function to load content dynamically into the tab-content area
function loadTabContent(page, textContent) {
    // document.title = textContent;
    document.getElementById('header-text').textContent = textContent;

    // Fetch content from the corresponding HTML file
    console.log('loadTabContent function called for ' + page + ' textContent ' + textContent);
    fetch(page)
        .then(response => response.text())
        .then(html => {

            // console.log('loadTabContent content ' + content);
            // Insert the fetched content into the tab-content div
            document.getElementById('main-tab-content').innerHTML = html;

            // Call additional functions based on the page
            if (page === 'world-control.html') {
                loadWorldStatus(); // Load world status for "World Control" tab
            } else if (page === 'resource-status.html') {
                loadResourceStatus();
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
function loadResourceStatus() {
    //fetchChartDataAndCreateChart('totalworldresourses', 'worldWideResourceChart');

}

// Function to fetch data and create the chart
function fetchWorldStockChartDataAndCreateChart(apiResource, htmlChartElementId) {
    console.log('fetchChartDataAndCreateChart htmlChartElementId ' + htmlChartElementId);
    fetch('http://localhost:8080/api/data/' + apiResource)
        .then(response => response.json())
        .then(data => {
            console.log('fetchChartDataAndCreateChart data received:', data, ' ', htmlChartElementId); // Debugging line
            createChart(data, apiResource, htmlChartElementId);  // Call createChart with fetched data
        })
        .catch(error => console.error('Error fetching data:', error));
}

// Function to create the chart (in resource-status tab)
function createChart(data, apiResource, htmlChartElementId) {
    console.log('createChart received data:', data, ' apiResource:', apiResource, ' htmlChartElementId:', htmlChartElementId);
    const labels = data.map(item => item.label);
    const values = data.map(item => item.data);

    const grapharea = document.getElementById(htmlChartElementId).getContext('2d');
    let chartStatus = Chart.getChart(htmlChartElementId); // <canvas> id
    if (chartStatus != undefined) {
        chartStatus.destroy();
    }
    new Chart(grapharea, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Last day quantity',
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
            },
            plugins: {
                title: {
                    display: true,
                    text: 'World Resources',
                    padding: {
                        top: 10,
                        bottom: 30
                    }
                }
            }

        }
    });
}






let currentDay = 0;

function advanceDay() {
    console.log('advanceDay');
    currentDay++;
    document.getElementById('world-advance-button').textContent = `World advance button has been pressed ${currentDay} times.`;

    // document.getElementById('world-status').textContent = `World is at day ${currentDay}.`;

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
    loadWorldStatus();
}

function loadWorldStatus() {
    fetchWorldStockChartDataAndCreateChart('totalworldresourses', 'worldWideResourceChart');
    renderGlobalMarketPriceChart();
    fetch('http://localhost:8080/api/world/status')
        .then(response => response.json())
        .then(data => {
            console.log('World status data:', data); // Debugging log
            createCompanyDayReportsTable(data);  // Populate the table with the data
        })
        .catch(error => console.error('Error fetching world status:', error));
}

// Function to populate the table with the status data
function createCompanyDayReportsTable(data) {
    const tableContainer = document.getElementById('company-day-reports');
    tableContainer.innerHTML = ''; // Clear any existing table content

    const table = document.createElement('table');

    table.style.borderCollapse = 'collapse';

    const tbody = document.createElement('tbody');

    // Loop through each key-value pair and create a row
    for (const [key, value] of Object.entries(data)) {
        const row = document.createElement('tr');

        const keyCell = document.createElement('td');
        keyCell.innerHTML = value.label;
        keyCell.style.fontWeight = 'bold';
        keyCell.style.padding = '8px';
        keyCell.style.border = '1px solid #A9A9A9'

        // Set fixed width of company identifier column:
        keyCell.style.width = '300px'; // Set fixed width for key cells

        const valueCell = document.createElement('td');
        valueCell.innerHTML = value.value;
        valueCell.style.padding = '8px';
        valueCell.style.border = '1px solid #A9A9A9'

        row.appendChild(keyCell);
        row.appendChild(valueCell);
        tbody.appendChild(row);
    }

    table.appendChild(tbody);
    tableContainer.appendChild(table);
}

// Call loadWorldStatus when the "World Control" tab is loaded
document.addEventListener('DOMContentLoaded', () => {
    loadTabContent('world-control.html', 'World Control');
    renderGlobalMarketPriceChart();
});


// Global prices:
// resources/static/js/main.js

// Fetches data from the REST API
async function fetchGlobalMarketPrices(usageCategory, country) {
    const params = new URLSearchParams();
    params.append('country', country);
    if (usageCategory) {
        params.append('usagecategory', usageCategory);
    }
    // Build the full URL with query parameters
    const url = `/api/data/marketpricesdayend${params.toString() ? '?' + params.toString() : ''}`;

    // Fetch the data
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }
    return response.json();
}

// Generates Chart.js-compatible data from the API response
function generateGlobalMarketPriceChartData(data) {
    const countries = data.countries;
    const skuData = data.skuData;

    const datasets = skuData.map((skuEntry, index) => {
        return {
            label: skuEntry.sku,
            data: skuEntry.prices,
            backgroundColor: `hsl(${index * 60}, 70%, 60%)`,
            borderColor: `hsl(${index * 60}, 70%, 50%)`,
            borderWidth: 1
        };
    });

    return {
        labels: countries,
        datasets: datasets
    };
}

// Renders the Chart.js bar chart
async function renderGlobalMarketPriceChart() {
    const data = await fetchGlobalMarketPrices("country-00000");
    const chartData = generateGlobalMarketPriceChartData(data);

    const ctx = document.getElementById('worldMarketPriceChart').getContext('2d');
    let chartStatus = Chart.getChart('worldMarketPriceChart'); // <canvas> id
    if (chartStatus != undefined) {
        chartStatus.destroy();
    }
    new Chart(ctx, {
        type: 'bar',
        data: chartData,
        options: {
            indexAxis: 'y', // Set to 'y' for horizontal bars
            responsive: false,  // Disable responsive resizing
            scales: {
                x: {
                    stacked: false,
                    title: {
                        display: true,
                        text: 'Country'
                    }
                },
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Price (USD)'
                    }
                }
            },
            plugins: {
                legend: {
                    display: false // Hides legend box, since we're putting labels on bars
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return `${context.dataset.label}: $${context.parsed.y.toFixed(0)}`;
                        }
                    }
                },
                datalabels: {
                    anchor: 'center',         // Position label in the center of the bar
                    align: 'right',          // Align label in the center of the bar
                    color: 'black',           // Text color
                    font: {
                        weight: 'bold'
                    },
                    formatter: function(value, context) {
                        return `${context.dataset.label}: $${value.toFixed(0)}`; // Label with good name and price
                    }
                }
            }
        },
        plugins: [ChartDataLabels]
    });
}




