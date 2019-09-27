# Portal de gestión para empresas sharing

## Modelamineto de ontologias

### Diagrama

![Diagrama](recursos/diagrama_ontologia.png)

### Detalle de ontologia

#### HG\_VehicleType

| Nombre | Tipo | Requerido | Encriptado | Descripcion |
| --- | --- | --- | --- | --- |
| **id** | *number* | si | no | Identificador del tipo de vehiculo |
| **typeName** | *string* | si | no | Nombre del tipo de vehiculo |
| **maxPassage** | *number* | si | no | Capacidad de pasajeros del vehiculo |

#### HG\_VehicleState

| Nombre | Tipo | Requerido | Encriptado | Descripcion |
| --- | --- | --- | --- | --- |
| **id** | *number* | si | no | Identificador del estado del vehiculo |
| **stateName** | *string* | si | no | Nombre del estado del vehiculo |
| **description** | *string* | no | no | Descripcion del estado del vehiculo |

#### HG\_DamageType

| Nombre | Tipo | Requerido | Encriptado | Descripcion |
| --- | --- | --- | --- | --- |
| **id** | *number* | si | no | Identificador del tipo de daño del vehiculo |
| **damageName** | *string* | si | no | Nombre del tipo de daño del vehiculo |
| **description** | *string* | no | no | Descripcion del tipo de daño del vehiculo |

#### HG\_Vehicle

| Nombre | Tipo | Requerido | Encriptado | Descripcion |
| --- | --- | --- | --- | --- |
| **id** | *number* | si | no | Identificador unico del vehiculo |
| **idTag** | *string* | no | no | Identificador visible del vehículo, que sería la matricula en caso de coches y motos, y un identificador aleatorio en caso de los patines |
| **batteryLevel** | *number* | si | no | Nivel de batería del vehículo, dado que todos serán eléctricos |
| **batteryTemp** | *number* | si | no | Temperatura actual de la batería |
| **totalKM** | *number* | si | no |   |
| **vehicleType** | *number* | si | no | Referencia a HG\_VehicleType |
| **position** | *geometry-point* | si | no | Número de KM totales en servicio del vehículo |
| **damageList** | *array* | no | no | Lista de daños del vehiculo( no tengo muy claro como realizar la relacion con la ontologia de HG\_DamageType cuando se susa el tipo array por lo que no es obligatorio) |
| **vehicleState** | *number* | si | no | Referencia a HG\_VehicleState |
| **hasDamage** | *boolean* | si | no | El vehiculo tiene algun tipo de daño |
| **DamageType** | *number* | no | no | Referencia a HG\_DamageType |

#### HG\_User

| Nombre | Tipo | Requerido | Encriptado | Descripcion |
| --- | --- | --- | --- | --- |
| **id** | *number* | si | no | Identificador unico del usuario |
| **userName** | *string* | si | no | Nombre de usuario |
| **password** | *string* | si | si | Contraseña del usuario |
| **minBonus** | *number* | no | no | Número de minutos que el usuario tiene en cuenta para gastar |
| **email** | *string* | si | no | Correo del usuario |

#### HG\_VehicleUse

| Nombre | Tipo | Requerido | Encriptado | Descripcion |
| --- | --- | --- | --- | --- |
| **id** | *number* | si | no | Idetntificador unico de usuario |
| **initTimeStamp** | *timestamp* | si | no | Tiempo de inició de uso |
| **pauseTime** | *number* | no | no | Número de minutos en pausa del vehículo, que se tarificarán de diferente forma |
| **endTimeStamp** | *timestamp* | no | no | Tiempo de finalización de uso |
| **totalMinutes** | *number* | si | no | Número de minutos totales del uso |
| **avgSpeed** | *number* | si | no | Velocidad media del uso |
| **iniPoint** | *geometry-point* | si | no | Punto de inicio del uso |
| **endPoint** | *geometry-point* | no | no | Punto final del uso |
| **user** | *number* | si | no | Referencia a HG\_User |
| **vehicle** | *number* | si | no | Referencia a HG\_Vehicle |

#### HG\_Notification

* No creado
