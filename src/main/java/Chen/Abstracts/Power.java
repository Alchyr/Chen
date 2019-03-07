package Chen.Abstracts;

import Chen.ChenMod;
import Chen.Patches.HiDefPowerPatch;
import Chen.Util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Power extends AbstractPower {
    protected PowerStrings powerStrings()
    {
        return CardCrawlGame.languagePack.getPowerStrings(ID);
    }
    protected String[] descriptions;
    protected AbstractCreature source;

    public Power(String name, PowerType powerType, boolean isTurnBased, AbstractCreature owner, AbstractCreature source, int amount)
    {
        this.ID = ChenMod.makeID(name);
        this.isTurnBased = isTurnBased;

        PowerStrings powerStrings = powerStrings();

        this.name = powerStrings.NAME;
        this.descriptions = powerStrings.DESCRIPTIONS;

        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.type = powerType;

        this.img = TextureLoader.getPowerTexture(this.name);

        Texture HiDefImage = TextureLoader.getHiDefPowerTexture(this.name);
        if (HiDefImage != null)
            HiDefPowerPatch.HiDefImage.img84.set(this, HiDefImage);


        this.updateDescription();
    }
}
