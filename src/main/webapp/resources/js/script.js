jQuery(document).ready(
    function($) {
        $("#AddMouvBtn").click(function(event) {
            var bSigne;
            var montant = $('#montantMouv').val();
            var label = $("#labelMouv").val();
            var signe = $('input[name=signeMouv]:checked', '#FormAddMouv').val();
            //.prop("checked") ? true : false;
            if (signe == "-"){
                bSigne = false;
            }else if(signe == "+"){
                bSigne = true;
            }else
                alert('Erreur signe');

            $("#AddMouvBtn").prop("disabled", true);

            $.ajax({
                url : '/addMouvement',
                type : 'POST',
                data : {
                    'montant' : montant,
                    'label' : label,
                    'signe' : bSigne
                },
                dataType: "json",
                success : function(response){
                    alert('success (response): '+response.Code);
                },
                error : function(response, statut, erreur){
                    alert('failed (response): '+response.Code);
                },
                complete : function(resultat, statut){
                    $("#AddMouvBtn").prop("disabled", false);
                }
            });
        });

    });