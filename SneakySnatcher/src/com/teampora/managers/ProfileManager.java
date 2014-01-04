package com.teampora.managers;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.teampora.adventure.MapSelection;
import com.teampora.credits.Credits;
import com.teampora.interfaces.IActionResolver;
import com.teampora.managers.SettingsManager.FileType;
import com.teampora.profile.Profile;
import com.teampora.tools.Gloves;
import com.teampora.tools.Hammer;
import com.teampora.tools.Scores;
import com.teampora.tools.Tools;
import com.teampora.tools.Tools.ToolType;

public class ProfileManager{
	private static final String LOG_TAG = "PROFILE_SERVICE";
    // the location of the profile data file
    private static final String PROFILE = "data/ssprofile";
    
    private static final String SUFFIX = ".json";
    
    

    // the loaded profile (may be null)
    public Profile profile;

    /**
     * Creates the profile service.
     */
    public ProfileManager(){}

    /**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}



	/**
	 * @param profile the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	

	public synchronized void setProfile(String json){
		
		json.replaceAll("[\\n]", "");
		
        // create the handle for the profile data file
        FileHandle profileDataFile = null;
        
        SettingsManager.setVersion((SettingsManager.PROFILE_VERSION + 1) % 5);
        
        
        if(Gdx.files.isExternalStorageAvailable()){
        	profileDataFile = SettingsManager.getFile(PROFILE + SettingsManager.PROFILE_VERSION + SUFFIX, FileType.External);
        }else if(Gdx.files.isLocalStorageAvailable()){
        	profileDataFile = SettingsManager.getFile(PROFILE + SettingsManager.PROFILE_VERSION + SUFFIX, FileType.Local);
        }
        
        String encrypted_json = Base64Coder.encodeString( json );
        
        profileDataFile.writeString( encrypted_json , false );
        
        
        // detach the previous profile
        profile = null;
        
        // get the updated one
        profile = retrieveProfile();
        
        // save the new profile persistently
        persist();
        
        Gdx.app.log("Profile Manager " + SettingsManager.PROFILE_VERSION , "writing json..." + json );
	}

	/**
     * Retrieves the player's profile, creating one if needed.
     */
    public Profile retrieveProfile(){
  

    	Gdx.app.log("PROFILE MANAGER", SettingsManager.PROFILE_VERSION + "");
    	
        // if the profile is already loaded, just return it
        if( profile != null ) return profile;

        // create the handle for the profile data file
        FileHandle profileDataFile = null;
        
        if(Gdx.files.isExternalStorageAvailable())
        	profileDataFile = SettingsManager.getFile(PROFILE + SettingsManager.PROFILE_VERSION + SUFFIX, FileType.External);
        else if(Gdx.files.isLocalStorageAvailable()){
        	profileDataFile = SettingsManager.getFile(PROFILE + SettingsManager.PROFILE_VERSION + SUFFIX, FileType.Local);
        }

        
        // create the JSON utility object
        Json json = new Json();
        json.setOutputType(OutputType.json);

        // check if the profile data file exists
        if( profileDataFile.exists() ) {
        	Gdx.app.log(LOG_TAG, "EXIST");
            // load the profile from the data file
            try {

                // read the file as text
                String profileAsCode = profileDataFile.readString();

                // decode the contents
                String profileAsText = Base64Coder.decodeString( profileAsCode );
                

                

                // restore the state
                profile = json.fromJson( Profile.class, profileAsText );

            } catch( Exception e ) {
            	e.printStackTrace();
            	Gdx.app.log(LOG_TAG, "ERROR");
                // recover by creating a fresh new profile data file;
                // note that the player will lose all game progress
                profile = createNew();
                persist( profile );

            }

        } else {
            // create a new profile data file
        	Gdx.app.log("PRIFLE", "NOT EXIST");
            profile = createNew();
    		
            persist( profile );
        }

        // return the result
        return profile;
    }
    
    /*
     *  createNew profile creates a new profile
     *  due to non exstant of local profile
     *  sets all default to tools ,etc 
     */
    private Profile createNew(){
    	/*
    	 *  create a profile due to null profile
    	 */
    	Profile profile = new Profile();
		
    	/*
    	 *  set coins
    	 */
		profile.setCoins(0);
		

		/*
		 * set tries
		 */
		profile.setTries(0);
		
		/*
		 *  set the scores back to zero
		 */
		List<Scores> scores = new ArrayList<Scores>();
		for(int i = 1;i <= 5 ; i ++){
			Scores score =  new Scores.Builder().setFileName("data/scores.txt")
							.setFileType(FileType.Internal)
							.build(i);
			scores.add(score);
		}
		
		/*
		 *  getting the tools from internal storage
		 *  stored data will be erased
		 */
		ArrayList<Hammer> hammer = new ArrayList<Hammer>();
		ArrayList<Gloves> gloves = new ArrayList<Gloves>();
		ArrayList<Credits> credits = new ArrayList<Credits>();
		for(int i = 1 ;i<= 5; i++){
			Hammer h = (Hammer)new Tools.ToolBuilder()
					.fileName("data/hammer.txt")
					.fileType(FileType.Internal)
					.id(i)
					.toolType(ToolType.Hammer)
					.createTool()
					.getCreatedTool();
			hammer.add(h);
			Gloves g = (Gloves) new Tools.ToolBuilder()
						.fileName("data/gloves.txt")
						.fileType(FileType.Internal)
						.id(i)
						.toolType(ToolType.Gloves)
						.createTool()
						.getCreatedTool();
			gloves.add(g);
		}
		
		int mapCount = SettingsManager.getLineCount("data/map.txt", FileType.Internal);
		List<MapSelection> maps = new ArrayList<MapSelection>();
		for(int i = 1; i <= mapCount ; i++){
			MapSelection map = new MapSelection.Builder()
							       .fileName("data/map.txt")
							       .fileType(FileType.Internal)
							       .id(i).build();
			maps.add(map);
		}
		
		int count = SettingsManager.getLineCount("data/credits.txt", FileType.Internal);
		for(int i =1 ; i <= count ; i ++){
			Credits credit = new Credits.Builder()
							.filename("data/credits.txt")
							.filetype(FileType.Internal)
							.build(i);
			credits.add(credit);
		}
		
		/*
		 *  setting the profiles highscores
		 */
		profile.setHighScores(scores);
		/*
		 *  setting the profile's gloves
		 */
		profile.setGloves(gloves);
		/*
		 *  setting the profile's hammer
		 */
		profile.setHammers(hammer);
		/*
		 * setting the profile's credits
		 */
		profile.setCredits(credits);
		
		/*
		 *  setting the profile's maps
		 */
		profile.setMaps(maps);
		
		/*
		 *  return profile for usability
		 */
		
		
		return profile;
    }

    /**
     * Persists the given profile.
     */
    protected void persist(Profile profile){

        // create the JSON utility object
        Json json = new Json();

        // create the handle for the profile data file
        FileHandle profileDataFile = null;
        
        if(Gdx.files.isExternalStorageAvailable())
        	profileDataFile = SettingsManager.getFile(PROFILE + SettingsManager.PROFILE_VERSION + SUFFIX, FileType.External);
        else if(Gdx.files.isLocalStorageAvailable()){
        	profileDataFile = SettingsManager.getFile(PROFILE + SettingsManager.PROFILE_VERSION + SUFFIX, FileType.Local);
        }
        

        // convert the given profile to text
        String profileAsText = json.toJson( profile );

        // encode the text
        String profileAsCode = Base64Coder.encodeString( profileAsText );

        // write the profile data file
        profileDataFile.writeString( profileAsCode, false ); 
    }

    /**
     * Persists the player's profile.
     * <p>
     * If no profile is available, this method does nothing.
     */
    public void persist(){
        if( profile != null ) {
            persist( profile );
        }
    }
    
}