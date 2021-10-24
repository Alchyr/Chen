package Chen.Abstracts;

import Chen.Util.CardInfo;
import Chen.Util.TextureLoader;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class AnimatedShiftCard extends ShiftChenCard {
    private String[] textureFramesA;
    private String[] textureFramesB;

    private int frameIndex;
    private float frameTime;
    private float frameRate;

    public AnimatedShiftCard(CardInfo cardInfo, boolean upgradesDescription, float frameRate)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardInfo.cardType, cardInfo.cardTarget, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription, false, frameRate);
    }
    public AnimatedShiftCard(CardInfo cardInfo, CardType cardTypeB, boolean upgradesDescription, float frameRate)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardTypeB, cardInfo.cardTarget, cardInfo.cardTarget, cardInfo.cardRarity, upgradesDescription, false, frameRate);
    }
    public AnimatedShiftCard(CardInfo cardInfo, CardType cardTypeB, CardTarget cardTargetB, boolean upgradesDescription, float frameRate)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardTypeB, cardInfo.cardTarget, cardTargetB, cardInfo.cardRarity, upgradesDescription, false, frameRate);
    }
    public AnimatedShiftCard(CardInfo cardInfo, CardType cardTypeB, CardTarget cardTargetB, boolean upgradesDescription, boolean preview, float frameRate)
    {
        this(cardInfo.cardName, cardInfo.cardCost, cardInfo.cardType, cardTypeB, cardInfo.cardTarget, cardTargetB, cardInfo.cardRarity, upgradesDescription, preview, frameRate);
    }

    public AnimatedShiftCard(String cardName, int cost, CardType cardTypeA, CardType cardTypeB, CardTarget cardTargetA, CardTarget cardTargetB, CardRarity rarity, boolean upgradesDescription, boolean preview, float frameRate)
    {
        super(cardName, cost, cardTypeA, cardTypeB, cardTargetA, cardTargetB, rarity, upgradesDescription, preview);

        textureFramesA = TextureLoader.getAnimatedCardTextureStrings(cardName, cardTypeA);
        textureFramesB = TextureLoader.getAnimatedShiftedCardTextureStrings(cardName, cardTypeB);

        this.frameIndex = 0;
        this.frameRate = frameRate;
        this.frameTime = frameRate;
    }

    @Override
    public void shift(boolean form) {
        if (this.Form != form)
        {
            frameIndex = 0;
            frameTime = frameRate;
        }
        super.shift(form);
    }

    @Override
    public void render(SpriteBatch sb, boolean selected) {
        super.render(sb, selected);

        frameTime -= Gdx.graphics.getDeltaTime();
        if (frameTime <= 0) //Try not to use too many of these, since loadCardImage uses reflection so it's probably not very fast, though the images are saved
        {
            if (Form)
            {
                frameIndex = (frameIndex + 1) % textureFramesA.length;
                loadCardImage(textureFramesA[frameIndex]);
            }
            else
            {
                frameIndex = (frameIndex + 1) % textureFramesB.length;
                loadCardImage(textureFramesB[frameIndex]);
            }


            frameTime = frameRate;
        }
    }
}
