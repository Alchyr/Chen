package Chen;

import Chen.Abstracts.ShiftChenCard;
import Chen.Actions.ChenActions.IncrementSpellsPlayedAction;
import Chen.Cards.Attacks.Shot;
import Chen.Interfaces.SpellCard;
import Chen.Patches.CardColorEnum;
import Chen.Patches.CharacterEnum;

import Chen.Patches.LibraryTypeEnum;
import Chen.Patches.TwoFormFields;
import Chen.Powers.Disoriented;
import Chen.Relics.Catnip;
import Chen.Relics.SacredCatnip;

import Chen.Character.Chen;

import Chen.Util.KeywordWithProper;
import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.interfaces.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.*;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpireInitializer
public class ChenMod implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber,
        EditCharactersSubscriber, EditKeywordsSubscriber, PostPowerApplySubscriber, OnStartBattleSubscriber,
        PostBattleSubscriber, StartGameSubscriber, OnCardUseSubscriber
{
    public static final Logger logger = LogManager.getLogger(ChenMod.class.getSimpleName());


    // Character color
    public static final Color CHEN_COLOR = CardHelper.getColor(229, 160, 139);

    // Card backgrounds/basic images
    private static final String CHEN_ATTACK_BACK = "img/Character/CardGeneric/bg_attack.png";
    private static final String CHEN_POWER_BACK = "img/Character/CardGeneric/bg_power.png";
    private static final String CHEN_SKILL_BACK = "img/Character/CardGeneric/bg_skill.png";
    private static final String CHEN_ENERGY_ORB = "img/Character/CardGeneric/card_orb.png";
    private static final String CHEN_CARD_ENERGY_ORB = "img/Character/CardGeneric/card_small_orb.png";

    private static final String CHEN_ATTACK_PORTRAIT = "img/Character/CardGeneric/portrait_attack.png";
    private static final String CHEN_POWER_PORTRAIT = "img/Character/CardGeneric/portrait_power.png";
    private static final String CHEN_SKILL_PORTRAIT = "img/Character/CardGeneric/portrait_skill.png";
    private static final String CHEN_CARD_ENERGY_ORB_PORTRAIT = "img/Character/CardGeneric/card_large_orb.png";

    // Character images
    private static final String CHEN_BUTTON = "img/Character/CharacterButton.png";
    private static final String CHEN_PORTRAIT = "img/Character/CharacterPortrait.png";


    //Tracking
    public static int battleDisorientCount = 0;
    public static int spellsThisCombat = 0;

    
    public ChenMod()
    {
        logger.info("Subscribing to BaseMod hooks");
        BaseMod.subscribe(this);

        logger.info("Creating Chen Character Color");
        BaseMod.addColor(CardColorEnum.CHEN_COLOR, CHEN_COLOR,
                assetPath(CHEN_ATTACK_BACK), assetPath(CHEN_SKILL_BACK), assetPath(CHEN_POWER_BACK),
                assetPath(CHEN_ENERGY_ORB),
                assetPath(CHEN_ATTACK_PORTRAIT), assetPath(CHEN_SKILL_PORTRAIT), assetPath(CHEN_POWER_PORTRAIT),
                assetPath(CHEN_CARD_ENERGY_ORB_PORTRAIT), assetPath(CHEN_CARD_ENERGY_ORB));
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Adding Chen");
        BaseMod.addCharacter(new Chen(Chen.characterStrings.NAMES[1], CharacterEnum.CHEN),
                assetPath(CHEN_BUTTON), assetPath(CHEN_PORTRAIT), CharacterEnum.CHEN);
    }


    @Override
    public void receiveEditCards() {
        try {
            AutoAdd a = new AutoAdd("Chen")
                    .packageFilter("Chen.Cards");

            ClassPool pool = Loader.getClassPool();

            try {
                Collection<CtClass> foundClasses = a.findClasses(CustomCard.class);
                CustomCard card;

                for (CtClass ctClass : foundClasses) {
                    if (!ctClass.hasAnnotation(AutoAdd.Ignore.class)) {
                        Class<?> c = pool.getClassLoader().loadClass(ctClass.getName());
                        if (ShiftChenCard.class.isAssignableFrom(c)) {
                            card = (CustomCard) c.getConstructor(boolean.class).newInstance(true);
                        }
                        else {
                            card = (CustomCard) c.newInstance();
                        }

                        if (card instanceof SpellCard && !card.hasTag(AbstractCard.CardTags.HEALING))
                            spells.add((SpellCard) card);
                        BaseMod.addCard(card);
                        UnlockTracker.unlockCard(card.cardID);
                    }
                }
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var8) {
                throw new RuntimeException(var8);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics.");

        BaseMod.addRelicToCustomPool(new Catnip(), CardColorEnum.CHEN_COLOR);
        BaseMod.addRelicToCustomPool(new SacredCatnip(), CardColorEnum.CHEN_COLOR);
    }

    @Override
    public void receiveEditStrings()
    {
        String lang = getLangString();

        logger.info("Adding relic strings.");
        BaseMod.loadCustomStringsFile(RelicStrings.class, assetPath("localization/" + lang + "/RelicStrings.json"));

        logger.info("Adding card strings.");
        BaseMod.loadCustomStringsFile(CardStrings.class, assetPath("localization/" + lang + "/CardStrings.json"));

        logger.info("Adding character strings.");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, assetPath("localization/" + lang + "/CharacterStrings.json"));

        logger.info("Adding power strings.");
        BaseMod.loadCustomStringsFile(PowerStrings.class, assetPath("localization/" + lang + "/PowerStrings.json"));

        logger.info("Adding UI strings.");
        BaseMod.loadCustomStringsFile(UIStrings.class, assetPath("localization/" + lang + "/UIStrings.json"));
    }

    @Override
    public void receiveEditKeywords()
    {
        String lang = getLangString();

        logger.info("Adding keywords.");

        Gson gson = new Gson();
        String json = Gdx.files.internal(assetPath("localization/" + lang + "/Keywords.json")).readString(String.valueOf(StandardCharsets.UTF_8));
        KeywordWithProper[] keywords = gson.fromJson(json, KeywordWithProper[].class);

        if (keywords != null) {
            for (KeywordWithProper keyword : keywords) {
                BaseMod.addKeyword("chen", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                if (keyword.ID.equals("SPELL") && SpellCard.spellDescriptor.isEmpty()) {
                    SpellCard.spellDescriptor.add(keyword.PROPER_NAME);
                }
            }
        }
    }

    private static final List<SpellCard> spells = new ArrayList<>();
    public static AbstractCard returnTrulyRandomSpellInCombat() {
        if (spells.isEmpty())
        {
            //wat how
            return new Shot().getCopyAsSpellCard();
        }

        return (AbstractCard)spells.get(AbstractDungeon.cardRandomRng.random(spells.size() - 1)).getCopyAsSpellCard();
    }

    private String getLangString()
    {
        switch (Settings.language) {
			case ZHS:
                return "zhs";
            default:
                return "eng";
        }
    }


    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("Initializing ChenMod.");
        new ChenMod();
    }


    @Override
    public void receiveStartGame() {
        battleDisorientCount = 0;
        TwoFormFields.shiftsThisTurn = 0;
        spellsThisCombat = 0;
    }
    @Override
    public void receivePostPowerApplySubscriber(AbstractPower abstractPower, AbstractCreature target, AbstractCreature source) {
        //logger.info("Power applied: " + abstractPower.ID + " by " + source.name);
        if (abstractPower.ID.equals(Disoriented.POWER_ID) && source.isPlayer)
        {
            battleDisorientCount += abstractPower.amount;
        }
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        battleDisorientCount = 0;
        TwoFormFields.shiftsThisTurn = 0;
        spellsThisCombat = 0;
    }
    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        battleDisorientCount = 0;
        TwoFormFields.shiftsThisTurn = 0;
        spellsThisCombat = 0;
    }

    /*@Override
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new ResetShiftCountAction());
        return true;
    }*/

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        if (abstractCard instanceof SpellCard)
        {
            AbstractDungeon.actionManager.addToBottom(new IncrementSpellsPlayedAction());
        }
    }

    //I totally didn't copy this from Hubris, made by kiooeht.
    /*private static void autoAddCards() throws URISyntaxException, IllegalAccessException, InstantiationException, NotFoundException, ClassNotFoundException {
        ClassFinder finder = new ClassFinder();
        URL url = ChenMod.class.getProtectionDomain().getCodeSource().getLocation();
        finder.add(new File(url.toURI()));

        ClassFilter filter =
                new AndClassFilter(
                        new NotClassFilter(new InterfaceOnlyClassFilter()),
                        new NotClassFilter(new AbstractClassFilter()),
                        new ClassModifiersClassFilter(Modifier.PUBLIC),
                        new CardFilter()
                );
        Collection<ClassInfo> foundClasses = new ArrayList<>();
        ArrayList<AbstractCard> addedCards = new ArrayList<>();
        finder.findClasses(foundClasses, filter);

        for (ClassInfo classInfo : foundClasses) {
            CtClass cls = Loader.getClassPool().get(classInfo.getClassName());

            boolean isCard = false;
            CtClass superCls = cls;
            while (superCls != null) {
                superCls = superCls.getSuperclass();
                if (superCls == null) {
                    break;
                }
                if (superCls.getName().equals(AbstractCard.class.getName())) {
                    isCard = true;
                    break;
                }
            }
            if (!isCard) {
                continue;
            }

            logger.info("Card: " + classInfo.getClassName());

            AbstractCard card = (AbstractCard) Loader.getClassPool().getClassLoader().loadClass(cls.getName()).newInstance();

            BaseMod.addCard(card);
            addedCards.add(card);

        }
        for (AbstractCard c : addedCards)
        {
            UnlockTracker.unlockCard(c.cardID);
        }
    }*/


    public static String makeID(String partialID)
    {
        return "Chen:" + partialID;
    }
    public static String assetPath(String partialPath)
    {
        return "Chen/" + partialPath;
    }
}
