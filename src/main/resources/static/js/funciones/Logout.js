function CerrarSesion() {

    bootbox.confirm({
        message: "¿Esta seguro que desea cerrar sesion?",
        buttons: {
            confirm: {
                label: 'Aceptar',
                className: 'btn-primary'
            },
            cancel: {
                label: 'Cancelar',
                className: 'btn-secondary'
            }
        },
        callback: function (result) {
            if (result) {
                $("#frmCerrar").submit();
            }
        }
    });
}
