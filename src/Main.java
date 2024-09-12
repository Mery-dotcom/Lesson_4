import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 220;
    public static String bossDefence;
    public static int medicHealth = 200;
    public static int medicDamage = 0;
    public static int medicHealAmount = 15;
    public static boolean isMedicAlive = true;
    public static int[] heroesHealth = {270, 280, 250, 700, 350, 300, 280};
    public static int[] heroesDamage = {20, 15, 10, 5, 6, 0, 23};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Golem", "Lucky", "Witcher", "Thor"};
    public static boolean isGolemAlive = true;
    public static boolean isLuckyAlive = true;
    public static boolean isWitcherAlive = true;
    public static boolean isThorAlive = true;
    public static int roundNumber = 0;
public static boolean isBossStunned = true;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead && !isMedicAlive) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossAttack();
        medicHeal();
        witcherRevived();
        heroesAttack();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesHealth.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossAttack() {
        int totalDamage = bossDamage;
        if (isGolemAlive) {
            int damageToGolem = totalDamage / 5;
            if (heroesHealth[3] - damageToGolem < 0) {
                heroesHealth[3] = 0;
                isGolemAlive = false;
            } else {
                heroesHealth[3] -= damageToGolem;
            }
            totalDamage -= damageToGolem;
        }

        int allHeroes = isGolemAlive ? heroesHealth.length - 1 : heroesHealth.length;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i != 3) {
                if (isLuckyAlive && i == 4) {
                    Random random = new Random();
                    boolean avoidFromBoss = random.nextBoolean();
                    if (avoidFromBoss) {
                        System.out.println("Lucky avoided the Boss's blow");
                        continue;
                    } else {
                        System.out.println("Lucky isn't lucky");
                    }
                }
                if (heroesHealth[i] > 0) {
                    int damage = totalDamage / allHeroes;
                    if (heroesHealth[i] - damage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] -= damage;
                    }
                }
            }
        }
            if (isMedicAlive) {
                if (medicHealth - bossDamage < 0) {
                    medicHealth = 0;
                    isMedicAlive = false;
                } else {
                    medicHealth -= bossDamage;
                }
            }
        }

        public static void heroesAttack () {
            for (int i = 0; i < heroesDamage.length; i++) {
                if (heroesHealth[i] > 0 && bossHealth > 0) {
                    int damage = heroesDamage[i];
                    if (bossDefence == heroesAttackType[i]) {
                        Random random = new Random();
                        int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                        damage = heroesDamage[i] * coeff;
                        System.out.println("Critical Damage: " + damage);
                    }
                    if (heroesAttackType[i].equals("Thor")){
                        Random random = new Random();
                        boolean stunBoss = random.nextBoolean();
                        if (stunBoss) {
                            isBossStunned = true;
                            System.out.println("Thor stunned the Boss.");
                        }
                    }
                    if (bossHealth - damage < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - damage;
                    }
                }
            }
        }

        public static void medicHeal () {
            if (isMedicAlive) {
                for (int i = 0; i < heroesHealth.length; i++) {
                    if (heroesHealth[i] > 0 && heroesHealth[i] < 100) {
                        heroesHealth[i] += medicHealAmount;
                        if (heroesHealth[i] > 100) {
                            heroesHealth[i] = 100;
                        }
                        break;
                    }
                }
            }
        }

        public static void witcherRevived () {
            if (isWitcherAlive) {
                Random random = new Random();
                boolean reviveHero = random.nextBoolean();
                if (reviveHero) {
                    for (int i = 0; i < heroesHealth.length; i++) {
                        if (heroesHealth[i] <= 0) {
                            if (heroesHealth[5] > 0) {
                                heroesHealth[i] = heroesHealth[5];
                                System.out.println("Witcher revived a fallen hero.");
                                heroesHealth[5] = 0;
                                isWitcherAlive = false;
                            }else {
                                System.out.println("Witcher can't revive.");
                            }
                            break;
                        }
                    }
                }
            }
        }

        public static void printStatistics () {
            System.out.println("ROUND: " + roundNumber + "-------------");
            System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " " +
                    "defence: " + (bossDefence == null ? "No defence" : bossDefence));
            System.out.println("Medic Health: " + medicHealth + " alive: " + isMedicAlive);
            System.out.println("Golem Health: " + heroesHealth[3] + " alive: " + isGolemAlive);
            System.out.println("Lucky Health: " + heroesHealth[4] + " alive: " + isLuckyAlive);
            System.out.println("Witcher Health: " + heroesHealth[5] + " alive: " + isWitcherAlive);
            System.out.println("Thor Health: " + heroesHealth[6] + " alive: " + isThorAlive);
            for (int i = 0; i < heroesHealth.length; i++) {
                System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                        + " damage: " + heroesDamage[i]);
            }
        }
    }
