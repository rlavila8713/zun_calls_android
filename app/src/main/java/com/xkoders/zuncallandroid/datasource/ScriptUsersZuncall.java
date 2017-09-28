package com.xkoders.zuncallandroid.datasource;

public class ScriptUsersZuncall {

    public static final String TABLA_NAME = "zuncall_users";
    public static final String ID = "id";
    public static final String ID_SECO = "id_seco";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";
    public static final String AVATAR = "avatar";
    public static final String NAME = "name";

    public static final String SCRIPT_DELEE_TABLE = "DROP TABLE IF EXISTS " + TABLA_NAME + ";";
    public static final String SCRIPT_CREAR_TABLA = "CREATE TABLE " + TABLA_NAME + " ( "
            + ID + " INTEGER PRIMARY KEY, "
            + ID_SECO + " INTEGER, "
            + EMAIL + " TEXT, "
            + PASSWORD + " TEXT, "
            + USERNAME + " TEXT, "
            + AVATAR + " TEXT, "
            + NAME + " TEXT"
            + ");";

    public static final String[] columns = new String[]{ScriptUsersZuncall.ID,
            ScriptUsersZuncall.ID_SECO,
            ScriptUsersZuncall.EMAIL,
            ScriptUsersZuncall.PASSWORD,
            ScriptUsersZuncall.USERNAME,
            ScriptUsersZuncall.AVATAR,
            ScriptUsersZuncall.NAME};

    public static final String[] SCRIPT_INSERT_CREATE = new String[]{
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (1,67,'reinier.leyva@get.hlg.tur.cu','reinier','reinier.leyva','','Reinier Leyva Avila');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (2,65,'jorge.fernandez@get.hlg.tur.cu','jorge','jorge.fernandez','','Jorge Abdel Fernández Banderas');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + "," + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (3,68,'miguel.hernandez@get.hlg.tur.cu','miguel','miguel.hernandez','','Miguel Alejandro Hernández Pupo');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (4,53,'rafael.ramirez@get.hlg.tur.cu','rafael','rafael.ramirez','','Rafael Emilio Ramírez Batista');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (5 ,56,'carlos.guerrero@get.hlg.tur.cu','carlosenzo','carlos.guerrero','','Carlos Enzo Guerrero Ochoa');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (6,54,'katia.santiaguez@get.hlg.tur.cu','katia','katia.santiaguez','','Katia Santiaguez Rodriguez');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (7,55,'yadira.santiesteban@get.hlg.tur.cu','yadira','yadira.santiesteban','','Yadira Santiesteban Rosello');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (8,57,'miriela.alonso@get.hlg.tur.cu','miriela','miriela.alonso','','Miriela Alonso Martinez');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (9,63,'francisco.munoz@get.hlg.tur.cu','francisco','francisco.munoz','','Francisco Muñoz Tamayo');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (10,69,'raul.ponce@get.hlg.tur.cu','raul','raul.ponce','','Raul Ponce de Leon Mora');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (11,66,'jaime.lopez@get.hlg.tur.cu','jaime','jaime.lopez','','Jaime López García');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (12,60,'danae.pita@get.hlg.tur.cu','danae','danae.pita','','Danae Pita Cruz');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (13,59,'arturo.sanchez','arturo','arturo.sanchez','','Arturo Leandro Sanchéz Batista');",
            "INSERT INTO " + TABLA_NAME + " (" + ID + ", " + ID_SECO + ", " + EMAIL + ", " + PASSWORD + "," + USERNAME + "," + AVATAR + "," + NAME + ") " +
                    "VALUES (14,61,'yanara.proenza','yanara','yanara.proenza','','Yanara Proenza Ochoa');",
    };
}
