jQuery(document).ready(
    function($) {

        /***************************************************************/
        /**************   Initialisation des graphiques   **************/
        /***************************************************************/

        // Doughnut chart (ChartJS)
        var labelGraph = $("#labelGraph").val().slice(0, -1).split(",");
        var dataGraph = $("#dataGraph").val().slice(0, -1).split(",");
        var data = {
            labels: labelGraph,
            datasets: [
                {
                    data: dataGraph,
                    backgroundColor:$("#colorGraph").val().split(",")
                }]
        };
        var ctx = $("#myChart");
        var myDoughnutChart = new Chart(ctx, {
            type: 'doughnut',
            data: data
        });

        //BarChart (ChartJS)
        var dataBar = {
            labels: ["Revenus", "Dépenses"],
            datasets: [
                {
                    label: "Somme",
                    backgroundColor: [
                        '#36A2EB',
                        '#FF6384'
                    ],
                    //borderWidth: 1,
                    data: [$("#BarChartR").val(), $("#BarChartD").val()],
                }
            ]
        };
        var BarChart = $("#BarChart");
        var myBarChart = new Chart(BarChart, {
            type: 'doughnut',
            data: dataBar
        });

    });