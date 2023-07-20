package Chen.Character;

import Chen.Cards.Basic.Defend;
import Chen.Cards.Basic.GlitterPoke;
import Chen.Cards.Basic.PounceTurnTail;
import Chen.Cards.Basic.Strike;
import Chen.Patches.CardColorEnum;
import Chen.Relics.Catnip;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static Chen.ChenMod.assetPath;
import static Chen.Patches.TwoFormFields.getForm;

public class Chen extends CustomPlayer {
    private final static Logger logger = LogManager.getLogger(Chen.class.getSimpleName());

    public static final com.megacrit.cardcrawl.localization.CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("chen:Character");

    private static final int ENERGY_PER_TURN = 3;
    private static final int MAX_HP = 70;
    private static final int STARTING_GOLD = 99;
    private static final int CARD_DRAW = 5;
    private static final int ORB_SLOTS = 0;

    public static final boolean ChenHuman = true;
    public static final boolean ChenCat = false;

    private static final Color CHEN_COLOR_A = CardHelper.getColor(219, 69, 63);
    private static final Color CHEN_COLOR_B = CardHelper.getColor(14, 8, 9);


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

    public static final String SKELETON_JSON = assetPath("img/Character/Spine/chen.json");
    public static final String SKELETON_ATLAS = assetPath("img/Character/Spine/chen.atlas");

    private static final float[] layerSpeeds = new float[]{20.0F, 30.0F, -40.0F, 20.0F, 0.0F};

    public Chen(String name, PlayerClass setClass) {
        super(name,
                setClass,
                orbTextures, assetPath("img/Character/orb/vfx.png"), layerSpeeds,
                null, null);

        initializeClass(null, // required call to load textures and setup energy/loadout
                assetPath("img/Character/shoulder.png"), // human
                assetPath("img/Character/shoulder2.png"), // cat
                assetPath("img/Character/corpse.png"), // dead corpse
                getLoadout(), -20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        loadAnimation(
                SKELETON_ATLAS,
                SKELETON_JSON,
                1.0f);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTimeScale(0.83F);


        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0], characterStrings.TEXT[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> startingDeck = new ArrayList<>();

        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);
        startingDeck.add(Strike.ID);

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
        return new PounceTurnTail(true);
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> startingRelics = new ArrayList<>();

        startingRelics.add(Catnip.ID);
        UnlockTracker.markRelicAsSeen(Catnip.ID);

        return startingRelics;
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
        return "ATTACK_WHIFF_1";
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
        return characterStrings.NAMES[0];
    }
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return characterStrings.NAMES[1];
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
    public Color getCardTrailColor() {
        if (getForm(this))
            return CHEN_COLOR_A;
        return CHEN_COLOR_B;
    }
    @Override
    public Color getCardRenderColor() {
        if (getForm(this))
            return CHEN_COLOR_A;
        return CHEN_COLOR_B;
    }

    @Override
    public Color getSlashAttackColor() {
        if (getForm(this))
            return Color.WHITE.cpy();

        return Color.BLACK.cpy();
    }



    public void applyStartOfTurnRelics() {
        //Whatever your code is to add cards



        super.applyStartOfTurnRelics();
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
        return characterStrings.TEXT[1];
    }

    @Override
    public String getVampireText() {
        return characterStrings.TEXT[2];
    }
}
