# Generátor manažmentu prístupu

Tento skript vygeneruje manažment prístupu pre aplikáciu v aktuálnom adresári. Zdrojové súbory a ukážkové konfiguračné súbory sú stiahnuté z tohto [repozitára](https://github.com/MS-101/aosd-project). Môžete si tam aj prečítať podrobnejšie informácie o funkcionalite týchto aspektov.

Po aplikovaní týchto aspektov sa používateľ pri spustení aplikácie musí autentifikovať. Manažment prístupu je riadený pomocou konfiguračných súborov vo formáte json, ktoré sú opísané nižšie.

## Základná konfigurácia

Je definovaná v súbore **basic.json**. Musíte uviesť meno funkcie pred ktorou sa má spustiť autentifikácia (*main*), meno funkcie po ktorej sa má vypísať dodatočná inštrukčná správa na príkaz odhlásanie (*help*) a meno funkcie ktorá číta aplikačné príkazy (*parser*).

```
{
	"main": "path1.methodName1",
	"help": "path1.methodName2",
	"parser": "path1.methodName3"
}
```

## Práva

Sú definované v súbore **permission.json**.

```
[
	{
		"name": "name1",
		"description": "description1"
	},
	{
		"name": "name2",
		"description": "description2"
	}
]
```

## Role

Sú definované v súbore **roles.json**. Rola má priradené práva.

```
[
	{
		"name": "name1",
		"description": "description1",
		"permissions": ["permissionName1", "permissionName2", "permissionName3"]
	},
	{
		"name": "name2",
		"description": "description2",
		"permissions": ["permissionName1", "permissionName2"]
	}
]
```

## Používatelia

Sú definované v súbore **users.json**. Používatelia majú priradené role. Heslá v tomto súbore musia byť zašifrované pomocou algoritmu SHA-512.

```
[
	{
		"username": "username1",
		"password": "hashedPassword1",
		"roles": ["role1", "role2"]
	},
	{
		"username": "username2",
		"password": "hashedPassword2",
		"roles": ["role1"]
	}
]
```

## Politika prístupu

Je definovaná v súbore **policies.json**. Musíte uviesť presný názov funkcie, ktorá je riadená a zoznam práv, ktoré sú potrebné na jej vykonanie. Stačí aby aspoň jedna z priradených rolí používateľa mala dané právo.

```
[
	{
		"method": "methodName1",
		"permissions": ["permission1", "permission2"]
	},
	{
		"method": "methodName1",
		"permissions": ["permission1"]
	},
	{
		"method": "methodName1",
		"permissions": ["permission1"]
	}
]
```