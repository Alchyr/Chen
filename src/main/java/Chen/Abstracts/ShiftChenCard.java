package Chen.Abstracts;

import Chen.Interfaces.BetterOnDamageGiveSubscriber;
import Chen.Util.CardInfo;
import Chen.Util.TextureLoader;
import Chen.Variables.SpellDamage;
import basemod.BaseMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class ShiftChenCard extends BaseCard {
    protected boolean Form; //true = A, false = B

    private String nameA;
    private String nameB;

    protected String descriptionA;
    protected String descriptionB;

    private int baseDamageA;
    private int baseDamageB;
    private int baseBlockA;
    private int baseBlockB;
    private int baseMagicNumberA;
    private int baseMagicNumberB;

    private CardType cardTypeA;
    private CardType cardTypeB;

    protected CardTarget cardTargetA;
    protected CardTarget cardTargetB;

    private int damageUpgradeB;
    private int blockUpgradeB;
    private int magicUpgradeB;

    private String shiftImg = null;


    public ShiftChenCard(CardInfo cardInfo, boolean upgradesDescription)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }
    public ShiftChenCard(CardInfo cardInfo, CardType cardTypeB, boolean upgradesDescription)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardTypeB, cardInfo.cardTarget, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription);
    }
    public ShiftChenCard(CardInfo cardInfo, CardType cardTypeB, CardTarget cardTargetB, boolean upgradesDescription)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardTypeB, cardInfo.cardTarget, cardTargetB, cardInfo.cardRarity, upgradesDescription);
    }

    public ShiftChenCard(String cardName, int cost, CardType cardTypeA, CardType cardTypeB, CardTarget cardTargetA, CardTarget cardTargetB, CardRarity rarity, boolean upgradesDescription)
    {
        super(cardName, cost, cardTypeA, cardTargetA, rarity, upgradesDescription);

        this.cardTypeA = cardTypeA;
        this.cardTypeB = cardTypeB;

        this.cardTargetA = cardTargetA;
        this.cardTargetB = cardTargetB;

        shiftImg = TextureLoader.getShiftedCardTextureString(cardName, cardTypeB);
        //ChenMod.logger.info(cardName + " shift image: " + shiftImg);

        Form = true;

        this.nameA = cardStrings.NAME;
        this.nameB = cardStrings.EXTENDED_DESCRIPTION[0];

        this.descriptionA = cardStrings.DESCRIPTION;
        this.descriptionB = cardStrings.EXTENDED_DESCRIPTION[1];

        this.baseDamageA = 0;
        this.baseDamageB = 0;
        this.baseBlockA = 0;
        this.baseBlockB = 0;
        this.baseMagicNumberA = 0;
        this.baseMagicNumberB = 0;

        this.damageUpgradeB = 0;
        this.blockUpgradeB = 0;
        this.magicUpgradeB = 0;

        if (AbstractDungeon.isPlayerInDungeon()) {
            if (AbstractDungeon.player != null) {
                if (AbstractDungeon.player instanceof TwoFormCharacter)
                {
                    this.Form = ((TwoFormCharacter)AbstractDungeon.player).Form;
                }
            }
        }

        this.Shift(Form);
    }

    //Discourage use of inherited base methods
    @Override
    protected void setDamage(int DONOTUSE)
    {
        this.setDamage(DONOTUSE, DONOTUSE, 0, 0);
    }
    @Override
    protected void setBlock(int DONOTUSE)
    {
        this.setBlock(DONOTUSE, DONOTUSE, 0, 0);
    }
    @Override
    protected void setMagic(int DONOTUSE)
    {
        this.setMagic(DONOTUSE, DONOTUSE, 0, 0);
    }
    //Methods meant for constructor use

    @Override
    protected void setDamage(int damageA, int damageB)
    {
        this.setDamage(damageA, damageB, 0, 0);
    }
    @Override
    protected void setBlock(int blockA, int blockB)
    {
        this.setBlock(blockA, blockB, 0, 0);
    }
    @Override
    protected void setMagic(int magicA, int magicB)
    {
        this.setMagic(magicA, magicB, 0, 0);
    }
    protected void setDamage(int damageA, int damageB, int damageUpgradeA, int damageUpgradeB)
    {
        this.baseDamageA = damageA;
        this.baseDamageB = damageB;
        if (damageUpgradeA != 0 || damageUpgradeB != 0)
        {
            this.upgradeDamage = true;
            this.damageUpgrade = damageUpgradeA;
            this.damageUpgradeB = damageUpgradeB;
        }
        this.baseDamage = (Form ? baseDamageA : baseDamageB);
    }
    protected void setBlock(int blockA, int blockB, int blockUpgradeA, int blockUpgradeB)
    {
        this.baseBlockA = blockA;
        this.baseBlockB = blockB;
        if (blockUpgradeA != 0 || blockUpgradeB != 0)
        {
            this.upgradeBlock = true;
            this.blockUpgrade = blockUpgradeA;
            this.blockUpgradeB = blockUpgradeB;
        }
        this.baseBlock = (Form ? baseBlockA : baseBlockB);
    }
    protected void setMagic(int magicA, int magicB, int magicUpgradeA, int magicUpgradeB)
    {
        this.baseMagicNumberA = magicA;
        this.baseMagicNumberB = magicB;
        if (magicUpgradeA != 0 || magicUpgradeB != 0)
        {
            this.upgradeMagic = true;
            this.magicUpgrade = magicUpgradeA;
            this.magicUpgradeB = magicUpgradeB;
        }
        this.baseMagicNumber = this.magicNumber = (Form ? baseMagicNumberA : baseMagicNumberB);
    }



    public void Shift(boolean form)
    {
        if (form)
        {
            this.name = nameA;
            this.rawDescription = descriptionA;

            this.textureImg = img;
            loadCardImage(textureImg);

            this.type = cardTypeA;
            this.target = cardTargetA;

            if (!this.Form) //currently in other form
            {
                //save state
                this.baseDamageB = this.baseDamage;
                this.baseBlockB = this.baseBlock;
                this.baseMagicNumberB = this.baseMagicNumber;

            }

            this.baseDamage = baseDamageA;
            this.baseBlock = baseBlockA;
            this.baseMagicNumber = this.magicNumber = baseMagicNumberA;
        }
        else {
            this.name = nameB;
            this.rawDescription = descriptionB;

            this.textureImg = shiftImg;
            loadCardImage(textureImg);

            this.type = cardTypeB;
            this.target = cardTargetB;

            if (this.Form) //currently in other form
            {
                //save state
                this.baseDamageA = this.baseDamage;
                this.baseBlockA = this.baseBlock;
                this.baseMagicNumberA = this.baseMagicNumber;
            }

            this.baseDamage = baseDamageB;
            this.baseBlock = baseBlockB;
            this.baseMagicNumber = this.magicNumber = baseMagicNumberB;
        }
        this.Form = form;

        this.initializeTitle();
        this.initializeDescription();
        this.resetAttributes();
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            this.upgradeName();

            if (upgradeCost)
                this.upgradeBaseCost(costUpgrade);

            if (this.upgradesDescription)
            {
                this.descriptionA = cardStrings.UPGRADE_DESCRIPTION;
                this.descriptionB = cardStrings.EXTENDED_DESCRIPTION[2];

                this.rawDescription = Form ? descriptionA : descriptionB;
                this.initializeDescription();
            }

            if (upgradeDamage)
                this.upgradeDamage();

            if (upgradeBlock)
                this.upgradeBlock();

            if (upgradeMagic)
                this.upgradeMagicNumber();

            if (baseExhaust ^ upgExhaust) //one true, one false. so, different
                this.exhaust = upgExhaust;
        }
    }

    @Override
    protected void upgradeName() {
        ++this.timesUpgraded;
        this.upgraded = true;
        this.name = this.name + "+";
        this.nameA = this.nameA + "+";
        this.nameB = this.nameB + "+";
        this.initializeTitle();
    }

    @Override
    protected void upgradeDamage(int amount) {
        this.baseDamage += amount;
        this.upgradedDamage = true;

        this.baseDamageA += amount;
        this.baseDamageB += amount;
    }
    protected void upgradeDamage()
    {
        this.baseDamageA += damageUpgrade;
        this.baseDamageB += damageUpgradeB;

        this.baseDamage = Form ? baseDamageA : baseDamageB;
        this.upgradedDamage = true;
    }

    @Override
    protected void upgradeBlock(int amount) {
        this.baseBlock += amount;

        this.upgradedBlock = true;

        this.baseBlockA += amount;
        this.baseBlockB += amount;
    }
    protected void upgradeBlock()
    {
        this.baseBlockA += blockUpgrade;
        this.baseBlockB += blockUpgradeB;

        this.baseBlock = Form ? baseBlockA : baseBlockB;
        this.upgradedBlock = true;
    }

    @Override
    protected void upgradeMagicNumber(int amount) {
        this.baseMagicNumber += amount;
        this.magicNumber = this.baseMagicNumber;

        this.upgradedMagicNumber = true;

        this.baseMagicNumberA += amount;
        this.baseMagicNumberB += amount;
    }
    protected void upgradeMagicNumber()
    {
        this.baseMagicNumberA += magicUpgrade;
        this.baseMagicNumberB += magicUpgradeB;

        this.baseMagicNumber = Form ? baseMagicNumberA : baseMagicNumberB;
        this.magicNumber = this.baseMagicNumber;
        this.upgradedMagicNumber = true;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        return super.makeStatEquivalentCopy();
    }

    //return what is not currently the main data
    public String getShiftName()
    {
        return (Form ? nameB : nameA);
    }
    public String getShiftDescription()
    {
        String rawAltDescription = Form ? descriptionB : descriptionA;

        rawAltDescription = rawAltDescription.replaceFirst("chen:Shift. NL ", "");
        rawAltDescription = rawAltDescription.replaceFirst("chen:Shapeshift: Cat. NL ", "");
        rawAltDescription = rawAltDescription.replaceFirst("chen:Shapeshift: Human. NL ", "");

        return processDescriptionForShiftKeyword(rawAltDescription);
    }


    public String processDescriptionForShiftKeyword(String rawDescription)
    {
        StringBuilder processedDescription = new StringBuilder("");

        String[] rawDescriptionWords = rawDescription.split(" ");
        int numWords = rawDescriptionWords.length;

        for(int i = 0; i < numWords; ++i) {
            String word = rawDescriptionWords[i];

            StringBuilder lastChar = new StringBuilder(" ");
            if (word.length() > 0 && word.charAt(word.length() - 1) != ']' && !Character.isLetterOrDigit(word.charAt(word.length() - 1))) {
                lastChar.insert(0, word.charAt(word.length() - 1));
                word = word.substring(0, word.length() - 1);
            }

            String keywordTmp = word.toLowerCase();
            keywordTmp = this.dedupeKeyword(keywordTmp);

            if (GameDictionary.keywords.containsKey(keywordTmp)) {
                if (BaseMod.keywordIsUnique(keywordTmp))
                {
                    String prefix = BaseMod.getKeywordPrefix(keywordTmp);
                    word = word.replaceFirst(prefix, "");
                }

                processedDescription.append("#y").append(word).append(lastChar.toString());
            } else {
                if (word.equals("!D")) {
                    processedDescription.append(getColoredDamageString(!Form)).append(" ");
                } else if (word.equals("!B")) {
                    processedDescription.append(getColoredBlockString(!Form)).append(" ");
                } else if (word.equals("!M")) {
                    processedDescription.append(getColoredMagicString(!Form)).append(" ");
                } else if (word.equals("!SPELL")) {
                    processedDescription.append(getColoredSpellString(!Form)).append(" ");
                } else {
                    processedDescription.append(word).append(lastChar.toString());
                }
            }
        }

        return processedDescription.toString();
    }

    private String getColoredDamageString(boolean Form)
    {
        int baseDamage = Form ? baseDamageA : baseDamageB;
        int finalDamage = applyDamagePowers(baseDamage);

        String colorString = "#b";

        if (finalDamage < baseDamage)
        {
            colorString = "#r";
        }
        else if (finalDamage > baseDamage || isDamageModified)
        {
            colorString = "#g";
        }

        return colorString + finalDamage;
    }
    private String getColoredBlockString(boolean Form)
    {
        int baseBlock = Form ? baseBlockA : baseBlockB;
        int finalBlock = applyBlockPowers(baseBlock);

        String colorString = "#b";

        if (finalBlock < baseBlock)
        {
            colorString = "#r";
        }
        else if (finalBlock > baseBlock || isBlockModified)
        {
            colorString = "#g";
        }

        return colorString + finalBlock;
    }
    private String getColoredMagicString(boolean Form)
    {
        int baseMagic = Form ? baseMagicNumberA : baseMagicNumberB;

        String colorString = "#b";

        if (isMagicNumberModified)
        {
            colorString = "#g";
        }

        return colorString + baseMagic;
    }
    private String getColoredSpellString(boolean Form)
    {
        int baseSpell = Form ? baseMagicNumberA : baseMagicNumberB;

        int finalSpell = SpellDamage.getSpellDamage(this, baseSpell);

        String colorString = "#b";

        if (finalSpell < baseSpell)
        {
            colorString = "#r";
        }
        else if (finalSpell > baseSpell || isMagicNumberModified)
        {
            colorString = "#g";
        }

            return colorString + finalSpell;
    }

    private int applyDamagePowers(int baseDamage)
    {
        if (AbstractDungeon.player != null)
        {
            float dmg = (float)baseDamage;

            if (AbstractDungeon.player.hasRelic("WristBlade") && (this.costForTurn == 0 || this.freeToPlayOnce)) {
                dmg += 3.0F;
            }

            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                dmg = p.atDamageGive(dmg, this.damageTypeForTurn);
                if (p instanceof BetterOnDamageGiveSubscriber)
                {
                    dmg = ((BetterOnDamageGiveSubscriber) p).betterAtDamageGive(dmg, this.damageTypeForTurn, null);
                }
            }
            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                dmg = p.atDamageFinalGive(dmg, this.damageTypeForTurn);
            }

            if (dmg < 0.0F) {
                dmg = 0.0F;
            }

            return MathUtils.floor(dmg);
        }

        return baseDamage;
    }

    private int applyBlockPowers(int baseBlock)
    {
        if (AbstractDungeon.player != null)
        {
            float blk = (float)baseBlock;

            for (AbstractPower p : AbstractDungeon.player.powers)
            {
                blk = p.modifyBlock(blk);
            }

            if (blk < 0.0F) {
                blk = 0.0F;
            }

            return MathUtils.floor(blk);
        }

        return baseBlock;
    }

    private String dedupeKeyword(String keyword) {
        String retVal = GameDictionary.parentWord.get(keyword);
        return retVal != null ? retVal : keyword;
    }
}
