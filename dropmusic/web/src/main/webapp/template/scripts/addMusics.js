$( document ).ready(
    function () {
        let list = $("#list");
        let button = $("#addButton");
        button.click(
            function () {
                let musicName = prompt("Enter music name");
                list.append('<div><input type="text" name="model.musics.name" value="' + musicName + '" id="addAlbumAction_model_musics_name"/></div>');
            }
        );
    }
);