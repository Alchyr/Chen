package Chen.Util;

import Chen.ChenMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.HashMap;

import static Chen.ChenMod.assetPath;
import static Chen.ChenMod.logger;
import static Chen.Util.PowerImages.HiDefPowerPath;
import static Chen.Util.PowerImages.PowerPath;

public class TextureLoader {
    private static HashMap<String, Texture> textures = new HashMap<String, Texture>();

    /**
     * @param textureString - String path to the texture you want to load relative to resources,
     * Example: "img/ui/missingtexture.png"
     * @return <b>com.badlogic.gdx.graphics.Texture</b> - The texture from the path provided
     */
    public static Texture getTexture(final String textureString) {
        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                try
                {
                    return getTexture(assetPath("img/MissingImage.png"));
                }
                catch (GdxRuntimeException ex) {
                    logger.info("The MissingImage is missing!");
                    return null;
                }
            }
        }
        return textures.get(textureString);
    }
    public static Texture getTextureNull(final String textureString) {
        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                return null;
            }
        }
        return textures.get(textureString);
    }

    public static boolean testTexture(final String textureString) {
        try
        {
            Texture texture =  new Texture(textureString);

            texture.dispose();
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    public static Texture getPowerTexture(final String powerName)
    {
        String textureString = ChenMod.assetPath(PowerPath(powerName));
        return getTexture(textureString);
    }
    public static Texture getHiDefPowerTexture(final String powerName)
    {
        String textureString = ChenMod.assetPath(HiDefPowerPath(powerName));
        return getTextureNull(textureString);
    }

    public static String getAndLoadCardTextureString(final String cardName, final AbstractCard.CardType cardType)
    {
        String textureString = getCardTextureString(cardName, cardType);

        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                switch (cardType) {
                    case ATTACK:
                        textureString = assetPath("img/Cards/Attacks/default.png");
                        break;
                    case SKILL:
                        textureString = assetPath("img/Cards/Skills/default.png");
                        break;
                    case POWER:
                        textureString = assetPath("img/Cards/Powers/default.png");
                        break;
                    default:
                        textureString = assetPath("img/MissingImage.png");
                        break;
                }
            }
        }
        //no exception, file exists
        return textureString;
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType)
    {
        switch (cardType)
        {
            case ATTACK:
                return assetPath("img/Cards/Attacks/" + cardName + ".png");
            case SKILL:
                return assetPath("img/Cards/Skills/" + cardName + ".png");
            case POWER:
                return assetPath("img/Cards/Powers/" + cardName + ".png");
            default:
                return assetPath("img/Cards/UnknownCard.png");
        }
    }

    public static String[] getAnimatedCardTextureStrings(final String cardName, final AbstractCard.CardType cardType)
    {
        int frameCount = 0;

        boolean success = true;

        ArrayList<String> files = new ArrayList<>();

        while (success)
        {
            String fileName = getCardTextureString(cardName + (frameCount > 0 ? frameCount : ""), cardType);

            success = testTexture(fileName);

            if (success)
            {
                files.add(fileName);
            }

            frameCount++;
        }

        String[] returnFiles = new String[files.size()];

        for (int i = 0 ; i < returnFiles.length; i++)
        {
            returnFiles[i] = files.get(i);
        }

        return returnFiles;
    }
    public static String getAndLoadShiftedCardTextureString(final String cardName, final AbstractCard.CardType cardType)
    {
        String textureString = getShiftedCardTextureString(cardName, cardType);

        if (textures.get(textureString) == null) {
            try {
                loadTexture(textureString);
            } catch (GdxRuntimeException e) {
                switch (cardType) {
                    case ATTACK:
                        textureString = assetPath("img/Cards/Attacks/default.png");
                        break;
                    case SKILL:
                        textureString = assetPath("img/Cards/Skills/default.png");
                        break;
                    case POWER:
                        textureString = assetPath("img/Cards/Powers/default.png");
                        break;
                    default:
                        textureString = assetPath("img/MissingImage.png");
                        break;
                }
            }
        }
        //no exception, file exists
        return textureString;
    }
    public static String getShiftedCardTextureString(final String cardName, final AbstractCard.CardType cardType)
    {
        switch (cardType)
        {
            case ATTACK:
                return assetPath("img/Cards/Attacks/" + cardName + "_s.png");
            case SKILL:
                return assetPath("img/Cards/Skills/" + cardName + "_s.png");
            case POWER:
                return assetPath("img/Cards/Powers/" + cardName + "_s.png");
            default:
                return assetPath("img/Cards/UnknownCard.png");
        }
    }
    public static String[] getAnimatedShiftedCardTextureStrings(final String cardName, final AbstractCard.CardType cardType)
    {
        int frameCount = 0;

        boolean success = true;

        ArrayList<String> files = new ArrayList<>();

        while (success)
        {
            String fileName = getShiftedCardTextureString(cardName + (frameCount > 0 ? frameCount : ""), cardType);

            success = testTexture(fileName);

            if (success)
            {
                files.add(fileName);
            }

            frameCount++;
        }

        String[] returnFiles = new String[files.size()];

        for (int i = 0 ; i < returnFiles.length; i++)
        {
            returnFiles[i] = files.get(i);
        }

        return returnFiles;
    }


    private static void loadTexture(final String textureString) throws GdxRuntimeException {
        loadTexture(textureString, false);
    }

    private static void loadTexture(final String textureString, boolean linearFilter) throws GdxRuntimeException {
        Texture texture = new Texture(textureString);
        if (linearFilter)
        {
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
        else
        {
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }
        textures.put(textureString, texture);
    }
}