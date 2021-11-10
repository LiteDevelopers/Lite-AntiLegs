# SMC-AntiLegs
Plugin "Anty-nogi" na serwer minecraft spigot.

### Przykładowe użycie API 
```java
SMCAntiLegs smcAntiLegs = SMCAntiLegs.getInstance();
AntiLegsManager manager = smcAntiLegs.getAntiLegsManager();

manager.getAntiLegs("standard").peek(standardAntiLegs -> {
    standardAntiLegs.setDistance(40);
    standardAntiLegs.setCooldown(5);

    ItemStack item = standardAntiLegs.getItem().build();
    // [ ... ]
});

manager.registerAntiLeg(new YourCustomAntiLegs());
```
