# Lite-AntiLegs
Plugin "Anty-nogi" na serwer minecraft spigot.

Download [[link]](https://github.com/LiteDevelopers/Lite-AntiLegs/releases) 

### Przykładowe użycie API 
```java
LiteAntiLegs liteAntiLegs = LiteAntiLegs.getInstance();
AntiLegsManager manager = liteAntiLegs.getAntiLegsManager();

manager.getAntiLegs("standard").peek(standardAntiLegs -> {
    standardAntiLegs.setDistance(40);
    standardAntiLegs.setCooldown(5);

    ItemStack item = standardAntiLegs.getItem().build();
    // [ ... ]
});

manager.registerAntiLeg(new YourCustomAntiLegs());
```
