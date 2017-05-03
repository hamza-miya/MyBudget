jQuery(document).ready(
    function($) {

        /***************************************************************/
        /*****************   Mouvement handler   ***********************/
        /***************************************************************/

        $(".modifMouvBtn").click(function(event) {
            //Replace TD with input and create new button for validation
            var label = $(this).parent().parent().parent().find(".labelTd").text();
            $(this).parent().parent().parent().find(".labelTd").html('' +
                '<input type="text" id="labelMouvModif" class="form-control" value="'+label+'">');
            var montant = $(this).parent().parent().parent().find(".montantTd").text();
            montant = montant.replace(/\s/g,'');
            $(this).parent().parent().parent().find(".montantTd").html('' +
                '<input type="text" id="montantMouvModif" class="form-control" value="'+montant+'">');
            $(this).replaceWith('' +
                '<button type="button" id="secondBtnOk" rel="tooltip" title="Enregistrer" ' +
                'class="btn btn-info btn-simple btn-xs">' +
                '<i class="fa fa-floppy-o"></i></button>');

            $(".modifMouvBtn").prop("disabled", true);

            var id = $(this).val();

            $("#secondBtnOk").click(function(event) {

                var montantToSend = $("#montantMouvModif").val().replace(/-/g,'');
                montantToSend = montantToSend.replace(/€/g,'');

                $.ajax({
                    url: '/ModifyMouvement',
                    type: 'POST',
                    data: {
                        'montant': montantToSend,
                        'label': $("#labelMouvModif").val(),
                        'id': id
                    },
                    dataType: "json",
                    success: function (response) {
                        window.location.replace("/dashboard");
                    },
                    error: function (response, statut, erreur) {
                        $.notify({
                            icon: 'pe-7s-info',
                            message: "Veuillez saisir un montant sans lettres ni caractères spéciaux. <b>Merci :)</b>"

                        },{
                            type: 'danger',
                            timer: 3000,
                            placement: {
                                from: "top",
                                align: "center"
                            }
                        });
                    }
                });
            });
        });

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
                    window.location.replace("/dashboard");
                },
                error : function(response, statut, erreur){
                    $.notify({
                        icon: 'pe-7s-info',
                        message: "Veuillez remplir tous les champs et saisir un montant sans lettres ni caractères spéciaux. <b>Merci :)</b>"

                    },{
                        type: 'danger',
                        timer: 2000,
                        placement: {
                            from: "top",
                            align: "center"
                        }
                    });
                },
                complete : function(resultat, statut){
                    $("#AddMouvBtn").prop("disabled", false);
                    $('#myModal').modal('toggle');
                }
            });
        });

        //Remove Mouvement
        $(".deleteMouvBtn").click(function(event) {
            var id = $(this).val();
            $(".deleteMouvBtn").prop("disabled", true);
            $.ajax({
                url : '/deleteMouvement',
                type : 'POST',
                data : {
                    'id' : id
                },
                dataType: "json",
                complete : function(resultat, statut){
                    window.location.replace("/dashboard");
                    $(".deleteMouvBtn").prop("disabled", false);
                }
            });
        });

        /***************************************************************/
        /*****************   Projet handler   ***********************/
        /***************************************************************/

        //Add projet
        $("#AddProjBtn").click(function(event) {

            var label = $("#labelProj").val();
            var montantEp = $('#montantEp').val();
            var montantObj = $('#montantObj').val();
            if (montantObj == "" || montantEp == "" || label == ""){
                $.notify({
                    icon: 'pe-7s-info',
                    message: "Veuillez remplir tous les champs svp. <b>Merci :)</b>"
                },{
                    type: 'warning',
                    timer: 2000,
                    placement: {
                        from: "top",
                        align: "center"
                    }
                });
                return true;
            }

            $("#AddProjBtn").prop("disabled", true);

            $.ajax({
                url : '/addProjet',
                type : 'POST',
                data : {
                    'montantEp' : montantEp,
                    'montantObj' : montantObj,
                    'label' : label
                },
                dataType: "json",
                success : function(response){
                    var htmlProj = '<div class="col-md-3"><div class="card card-user"><div class="image" style="background-color: yellowgreen">' +
                        '<div class="row" style="text-align: right"><div class="col-md-6"></div><div class="col-md-6 nbIncr">' +
                        '<span class="montant_moisProj">+'+montantEp+'€</span></div></div><!-- img --></div><div class="content" style="min-height: 0;">' +
                        '<div class="author"> <img class="avatar border-gray" src="/resources/img/tirelire-economie.jpg"> <h4 class="title">' +
                        '<span class="labelProj">'+label+'</span><br><small><span class="montant_acquisProj">0.0</span>€</small> / <small>' +
                        '<span class="montant_objProj">'+montantObj+'</span>€</small> </h4></div>' +
                        '<div class="projProgress text-center">' +
                        '<progress value="0" max="'+montantObj+'"></progress>' +
                        '</div>' +
                        '</div><hr><div class="text-center">' +
                        '<button type="button" rel="" value="'+response.NewProjetId+'" title="Modifier" class="modifProjBtn btn btn-info btn-simple btn-xs"> <i class="fa fa-edit"></i></button>' +
                        '<button type="button" rel="tooltip" value="'+response.NewProjetId+'" title="Enregistrer" class="secondBtnSave invisible btn btn-info btn-simple btn-xs"> <i class="fa fa-floppy-o"></i></button>' +
                        '<button type="button" rel="tooltip" value="'+response.NewProjetId+'" title="Supprimer" class="deleteProjBtn btn btn-danger btn-simple btn-xs"> <i class="fa fa-times"></i></button></div></div></div>';
                    $("#rowListProjet").append(htmlProj);

                    //Add Listener for action button
                    modifyProjetListener();
                    saveProjetListener();
                    deleteProjetListener();

                    $.notify({
                        icon: 'pe-7s-info',
                        message: "Le projet <b>"+label+"</b> a été ajouté avec succès. <b>:)</b>"
                    },{
                        type: 'success',
                        timer: 2000,
                        placement: {
                            from: "top",
                            align: "center"
                        }
                    });
                },
                error : function(response, statut, erreur){
                    if(response.Code == "REDIRECT"){
                        window.location.replace("/dashboard");
                    }
                    $.notify({
                        icon: 'pe-7s-info',
                        message: "Veuillez saisir des montants sans lettres ni caractères spéciaux. <b>Merci :)</b>"
                    },{
                        type: 'danger',
                        timer: 2000,
                        placement: {
                            from: "top",
                            align: "center"
                        }
                    });
                },
                complete : function(resultat, statut){
                    $("#AddProjBtn").prop("disabled", false);
                    $('#myModal').modal('toggle');
                }
            });
        });

        //Call all action button listener
        modifyProjetListener();
        saveProjetListener();
        deleteProjetListener();

        function modifyProjetListener() {
            $(".modifProjBtn").click(function(event) {
                $(".modifProjBtn").prop("disabled", true);

                var labelProj = '';
                var montant_moisProj = '';
                var montant_objProj = '';

                var idProj = $(this).val();
                labelProj = $(this).parent().parent().find(".labelProj").text();
                montant_moisProj = $(this).parent().parent().find(".montant_moisProj").text().replace(/\+/g,'');
                montant_moisProj = montant_moisProj.replace(/€/g,'');
                montant_objProj = $(this).parent().parent().find(".montant_objProj").text();

                $(this).parent().parent().find(".labelProj").replaceWith(
                    '<input type="text" id="labelProj" class="form-control" value="'+labelProj+'">');
                $(this).parent().parent().find(".montant_moisProj").replaceWith(
                    '<input type="text" id="montant_moisProj" class="form-control" value="'+montant_moisProj+'">');
                $(this).parent().parent().find(".montant_objProj").replaceWith(
                    '<input type="text" id="montant_objProj" class="form-control" value="'+montant_objProj+'">');
                $(this).parent().find(".secondBtnSave").removeClass("invisible");
            });
        }

        function deleteProjetListener() {
            //Remove Projet
            $(".deleteProjBtn").click(function(event) {
                var id = $(this).val();
                var labelProjS = $(this).parent().parent().find(".labelProj").text();
                var cardP = $(this);
                $(".deleteProjBtn").prop("disabled", true);
                $.ajax({
                    url : '/deleteProjet',
                    type : 'POST',
                    data : {
                        'id' : id
                    },
                    dataType: "json",
                    success : function(resultat, statut){
                        cardP.parent().parent().parent().remove();
                        $.notify({
                            icon: 'pe-7s-info',
                            message: "Le projet <b>"+labelProjS+"</b> a correctement été supprimé. <b>:)</b>"
                        },{
                            type: 'info',
                            timer: 2000,
                            placement: {
                                from: "top",
                                align: "center"
                            }
                        });
                    },
                    error : function(response, statut, erreur){
                        $.notify({
                            icon: 'pe-7s-info',
                            message: "Une erreur est survenue, veuillez reessayer ultérieurement ou contacter " +
                            "l'administrateur. <b>Merci :)</b>"
                        },{
                            type: 'danger',
                            timer: 2000,
                            placement: {
                                from: "top",
                                align: "center"
                            }
                        });
                    },
                    complete : function(resultat, statut){
                        $(".deleteProjBtn").prop("disabled", false);
                        $(".modifProjBtn").prop("disabled", false);
                    }
                });
            });
        }

        function saveProjetListener() {
            $(".secondBtnSave").click(function(event) {
                $(".secondBtnSave").prop("disabled", true);
                var idProj = $(this).val();
                var sBtnSave = $(this);
                $.ajax({
                    url: '/ModifyProjet',
                    type: 'POST',
                    data: {
                        'label': $("#labelProj").val(),
                        'montant_mois': $("#montant_moisProj").val(),
                        'montant_obj': $("#montant_objProj").val(),
                        'id': idProj
                    },
                    dataType: "json",
                    success: function (response) {
                        var mObjProj = $("#montant_objProj").val();
                        var mAcqProj = $("#montant_acquisProj").val();
                        var mMoisProj = $("#montant_moisProj").val();
                        $('#montant_objProj').replaceWith('' +
                            '<span class="montant_objProj">'+mObjProj+'</span>');
                        $('#montant_moisProj').replaceWith('' +
                            '<span class="montant_moisProj">+'+mMoisProj+'€</span>');
                        $('#labelProj').replaceWith('' +
                            '<span class="labelProj">'+$("#labelProj").val()+'</span>');
                        //sBtnSave.parent().parent().find(".projProgress").html('' +
                            //'<progress value="'+mAcqProj+'" max="'+mObjProj+'"></progress>');

                        $.notify({
                            icon: 'pe-7s-check',
                            message: "Projet modifié avec succès. <b>:)</b>"
                        },{
                            type: 'info',
                            timer: 2000,
                            placement: {
                                from: "top",
                                align: "center"
                            }
                        });
                        $(".secondBtnSave").addClass("invisible");
                        $(".secondBtnSave").parent().find(".modifProjBtn").prop("disabled", false);

                    },
                    error: function (response, statut, erreur) {
                        $.notify({
                            icon: 'pe-7s-info',
                            message: "Veuillez saisir les champs correctement. <b>Merci :)</b>"

                        },{
                            type: 'danger',
                            timer: 2000,
                            placement: {
                                from: "top",
                                align: "center"
                            }
                        });
                    },
                    complete : function(resultat, statut){
                        $(".modifProjBtn").prop("disabled", false);
                        $(".secondBtnSave").prop("disabled", false);
                    }
                });
            });
        }

        function simulateProjetListener(){
            $('#progressInput').change(function () {

                var nbMonthSimulate = $('#progressInput').val();

                $('.each-projet').each(function () {
                    var mAcquisSimu = $(this).find(".montant_acquisProj").text();
                    var mMoisSimu = $(this).find(".montant_moisProj").text().replace(/\+/g,'');
                    mMoisSimu = mMoisSimu.replace(/€/g,'');

                });
            });

        }

    });