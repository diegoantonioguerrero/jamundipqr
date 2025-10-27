function mayus(e) {
    e.value = e.value.toUpperCase();
    e.value = removeAccents(e.value);
}

function removeAccents(str){
    return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
} ;

function mayusName(e) {
    let start = e.selectionStart;
    e.value = e.value.toUpperCase();
    e.value = removeAccentsName(e.value);
    setCursorPosition(e, start);
}

function setCursorPosition(elem, pos) {
    if (elem.setSelectionRange) {
        elem.setSelectionRange(pos, pos);
    } else if (elem.createTextRange) {
        var range = elem.createTextRange();
        range.collapse(true);
        range.moveEnd('character', pos);
        range.moveStart('character', pos);
        range.select();
    }
}

function removeAccentsName(str){
    //var texto = str.normalize('NFD').replace(/([^n\u0300-\u036f]|n(?!\u0303(?![\u0300-\u036f])))[\u0300-\u036f]+/gi,"$1").normalize();
    var texto = removeAccents2(str);
    return texto;
} ;

function minus(e) {
    e.value = e.value.toLowerCase();
}

function removeAccents2 (text) {
    var accents    = 'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž',
        accentsOut = "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuÑÑSsYyyZz",
        textNoAccents = [];

    for (var i in text) { 
        var idx = accents.indexOf(text[i]);
        if (idx !== -1)
            textNoAccents[i] = accentsOut.substr(idx, 1);
        else
            textNoAccents[i] = text[i];
    }

    return textNoAccents.join('');
}