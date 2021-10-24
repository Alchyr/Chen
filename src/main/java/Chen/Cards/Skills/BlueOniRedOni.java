package Chen.Cards.Skills;

import Chen.Abstracts.BaseCard;
import Chen.Actions.ChenActions.ShapeshiftAction;
import Chen.Cards.Attacks.BlueOni;
import Chen.Cards.Attacks.RedOni;
import Chen.Character.Chen;
import Chen.Patches.TwoFormFields;
import Chen.Util.CardInfo;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.List;

import static Chen.ChenMod.makeID;

public class BlueOniRedOni extends BaseCard {
    private final static CardInfo cardInfo = new CardInfo(
            "BlueOniRedOni",
            2,
            CardType.SKILL,
            CardTarget.NONE,
            CardRarity.UNCOMMON
    );

    public final static String ID = makeID(cardInfo.cardName);

    public BlueOniRedOni()
    {
        super(cardInfo, true);

        cardsToPreview = previewBlue;
    }

    private final AbstractCard previewBlue = new BlueOni(), previewRed = new RedOni();
    private float previewTimer = 3.0f;

    @Override
    public void upgrade() {
        super.upgrade();
        previewBlue.upgrade();
        previewRed.upgrade();
    }

    @Override
    public void update() {
        super.update();

        previewTimer -= Gdx.graphics.getRawDeltaTime();
        if (previewTimer < 0) {
            previewTimer = 3.0f;
            if (this.cardsToPreview == previewBlue) {
                this.cardsToPreview = previewRed;
            }
            else {
                this.cardsToPreview = previewBlue;
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!TwoFormFields.getForm()) {
            AbstractDungeon.actionManager.addToBottom(new ShapeshiftAction(this, Chen.ChenHuman));
        }

        AbstractCard blueOni = new BlueOni();
        AbstractCard redOni = new RedOni();

        if (upgraded)
        {
            blueOni.upgrade();
            redOni.upgrade();
        }

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(blueOni));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(redOni));
    }
}