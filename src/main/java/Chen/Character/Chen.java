package Chen.Character;

import Chen.Abstracts.TwoFormCharacter;
import Chen.Cards.Basic.Defend;
import Chen.Cards.Basic.PounceTurnTail;
import Chen.Cards.Basic.Strike;

import Chen.Cards.Other.GlitterPoke;
import Chen.Patches.CardColorEnum;
import Chen.Relics.Catnip;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static Chen.ChenMod.assetPath;
import static Chen.ChenMod.shiftsThisTurn;

public class Chen extends TwoFormCharacter {
    private final static Logger logger = LogManager.getLogger(Chen.class.getSimpleName());

    private static final int ENERGY_PER_TURN = 3;
    private static final int STARTING_HP = 65;
    private static final int MAX_HP = 65;
    private static final int STARTING_GOLD = 99;
    private static final int CARD_DRAW = 5;
    private static final int ORB_SLOTS = 0;

    public static final boolean ChenHuman = true;
    public static final boolean ChenCat = false;

    private static final Color CHEN_COLOR_A = CardHelper.getColor(219.0f, 69.0f, 63.0f);
    private static final Color CHEN_COLOR_B = CardHelper.getColor(14.0f, 8.0f, 9.0f);


    //TODO - ALL image files

    private static final String[] orbTextures = {
            assetPath("img/Character/orb/layer1.png"),
            assetPath("img/Character/orb/layer2.png"),
            assetPath("img/Character/orb/layer3.png"),
            assetPath("img/Character/orb/layer4.png"),
            assetPath("img/Character/orb/layer5.png"),
            assetPath("img/Character/orb/layer6.png"),
            assetPath("img/Character/orb/layer1d.png"),
            assetPath("img/Character/orb/layer2d.png"),
            assetPath("img/Character/orb/layer3d.png"),
            assetPath("img/Character/orb/layer4d.png"),
            assetPath("img/Character/orb/layer5d.png")
    };

    public Chen(String name, PlayerClass setClass) {
        super(name,
                setClass,
                orbTextures, assetPath("img/Character/orb/vfx.png"), null,
                new SpriterAnimation(assetPath("img/Character/Spriter/Human/human.scml")),
                new SpriterAnimation(assetPath("img/Character/Spriter/Cat/cat.scml")),
                CHEN_COLOR_A, CHEN_COLOR_B);

        initializeClass(null, // required call to load textures and setup energy/loadout
                assetPath("img/Character/shoulder.png"), // human
                assetPath("img/Character/shoulder2.png"), // cat
                assetPath("img/Character/corpse.png"), // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager


        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Black Cat of Ill Omens",
                "A shikigami's shikigami. NL " + "A bakeneko who can take human or cat form.",
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> startingDeck = new ArrayList<>();

        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);

        startingDeck.add(Defend.ID);
        startingDeck.add(Defend.ID);
        startingDeck.add(Defend.ID);
        startingDeck.add(Defend.ID);
        startingDeck.add(Defend.ID);

        startingDeck.add(PounceTurnTail.ID);
        startingDeck.add(GlitterPoke.ID);

        return startingDeck;
    }
    @Override
    public AbstractCard getStartCardForEvent() {
        return new PounceTurnTail();
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> startingRelics = new ArrayList<>();

        startingRelics.add(Catnip.ID);
        UnlockTracker.markRelicAsSeen(Catnip.ID);
        
        return startingRelics;
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);


    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("BYRD_DEATH", 0.2f); // TODO: Get a proper meow
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // Character select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_IRON_1";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return CardColorEnum.CHEN_COLOR;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {

        switch (Settings.language)
        {
            default:
                return "The Black Cat of Ill Omen";
        }
    }
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return "the Black Cat of Ill Omen";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Chen(this.name, this.chosenClass);
    }

    @Override
    public void applyStartOfCombatPreDrawLogic() {
        //this one goes first.
        super.applyStartOfCombatPreDrawLogic();
    }
    @Override
    public void applyStartOfCombatLogic() {
        //then this one.
        super.applyStartOfCombatLogic();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY
        };
    }

    @Override
    public String getSpireHeartText() {
        return "You sharpen your claws.";
    }

    @Override
    public String getVampireText() {
        return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. They smell like vampires. Vampires are no good.";
    }
}
