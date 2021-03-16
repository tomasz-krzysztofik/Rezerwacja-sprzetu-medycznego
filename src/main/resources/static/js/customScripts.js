let customScripts = {};
customScripts.showOrHide = function () {
    let x = document.getElementById("doctorsData");
    let y = document.getElementById("doctorsPWZ");
    if (document.getElementById("select").value == "Lekarz") {
        x.style.display = "block";
        y.disabled = false;
        y.required = true;
    } else {
        x.style.display = "none";
        y.disabled = true;
        y.required = false;
    }


};

customScripts.minTime = function () {
    let d = new Date();
    let year = d.getFullYear();
    let month = d.getMonth() + 1;
    if (month < 10)
        month = '0' + month;
    let days = d.getDate();
    if (days < 10)
        days = '0' + days;
    let limit = year + '-' + month + '-' + days;
    document.getElementById("maintenanceDate").min = limit;
    document.getElementById("installationDate").max = limit;
};


customScripts.showExaminations = function (chosenOption) {
    let url;
    if (chosenOption === "receptionist") {
        url = "/receptionistsPanel/findExaminationsForActiveDevices/" + document.getElementById("typeSelect").value;
    }
    if (chosenOption === "doctor") {
        url = "/doctorsPanel/showExaminationsForType/" + document.getElementById("typeSelect").value;
    }

    $("#selectExamination").empty();
    $.ajax({
        url: url,
        success: function (result) {

            let i;
            for (i = 0; i < result.length; i++) {
                let opt = document.createElement("option");
                opt.appendChild(document.createTextNode(result[i]));
                opt.value = result[i];
                document.getElementById("selectExamination").appendChild(opt);
            }
        }
    })
};


customScripts.startReceptionist = function () {
    customScripts.showExaminations("receptionist");
    customScripts.hideSubmit();
};


customScripts.hideSubmit = function () {
    document.getElementById("submitButton").style.display = "none";
    document.getElementById("searchBar").style.display = "block";
    document.getElementById("termID").style.display = "block";
};
customScripts.checkPatient = function () {
    let url = "/receptionistsPanel/searchPatient/" + document.getElementById("pesel").value;

    $.ajax({
        url: url,
        success: function (result) {

            if (result !== "null") {
                document.getElementById("submitButton").style.display = "block";
                document.getElementById("patientData").textContent = result;
                document.getElementById("iframe").src = "about:blank";
            } else {
                alert("Pacjent nie istnieje");
                document.getElementById("submitButton").style.display = "none";
                document.getElementById("patientData").textContent = " ";
                document.getElementById("iframe").src = "about:blank";

            }
        }
    })

};





