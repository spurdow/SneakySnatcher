package com.teampora.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teampora.adventure.Level;
import com.teampora.adventure.MapSelection;
import com.teampora.buttons.SneakyImageButton;
import com.teampora.buttons.SneakyLevelButton;
import com.teampora.game.State;
import com.teampora.groups.IndicatorBox;
import com.teampora.managers.SettingsManager;
import com.teampora.scrollpanes.SneakyScrollPane;
import com.teampora.scrollpanes.SneakyScrollPane.Indicator;
import com.teampora.sneakysnatcher.SneakySnatcher;
import com.teampora.utils.Assets;
import com.teampora.utils.Utils;

public class LevelScreen extends AbstractScreen implements Indicator{

	private Table background;
	
	private List<Level> levels;
	
	private SneakyScrollPane pageScroll;
	
	private SneakyImageButton backButton;
	
	private MapSelection map = null;
	
	private IndicatorBox indicatorBox;
	
	public LevelScreen(SneakySnatcher game, Stage stage, State state) {
		super(game, stage, state);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void renderSub(float delta) {
		// TODO Auto-generated method stub
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void showSub() {
		// TODO Auto-generated method stub
		/*
		 * find the selected map
		 */

		for(MapSelection nmap : game.screen.profile.getMaps()){
			if(nmap.isChoosen()){
				map = nmap;
				break;
			}
		}
		/*
		 *  after searching for the map still null
		 *  no other choice but to choose the first
		 */
		if(map == null){
			map = game.screen.profile.getMaps().get(0);
			map.setChoosen(true);
		}
		
		background = new Table();
		TextureRegionDrawable drawable = new TextureRegionDrawable(SettingsManager.getRegion(map.getFileName()));
		background.setBackground(drawable);
	
		background.setFillParent(true);
		
		backButton = new SneakyImageButton("Back",Assets.skin, Assets.leftArrow_icon);
		backButton.setPosition(0, 0);
		backButton.setSize(Utils.WIDTH_EXTRA_SMALL(), Utils.HEIGHT_NORMAL());
		backButton.addListener(new ClickListener(){

			/* (non-Javadoc)
			 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
			 */
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				game.setScreen(game.screen.getNextScreen(State.MAP));
				super.clicked(event, x, y);
			}
			
		});
		
		pageScroll = new SneakyScrollPane();
		pageScroll.setFlingTime(0.1f);
		pageScroll.setPageSpacing(100);
		pageScroll.setIndicatorListener(this);

		/*
		 * generate levels
		 */
		levels = map.getLevels();
		int maxTables = levels.size() / 15;
		indicatorBox = new IndicatorBox(maxTables , 0 , Assets.selected  , Assets.unselected);
		Gdx.app.log("LEVELS", maxTables+"");
		int levelNo = 0;
		final int row  = 3;
		final int col  = 5;
		for(int page = 0 ; page < maxTables ; page++ ){
			Table container = new Table().pad(50);
			for(int r = 0 ; r < row ; r++){
				for(int c = 0; c < col ; c++){
					
					SneakyLevelButton level = new SneakyLevelButton(Assets.skin , map.getButtonName() , levels.get(levelNo).getStarsAccumulated() , Assets.star_filled , Assets.star_unfilled );
					level.setText(++levelNo+"");
					final int levelNumber = levelNo;
					final int currentPage = page;
					level.addListener(new ClickListener(){

						/* (non-Javadoc)
						 * @see com.badlogic.gdx.scenes.scene2d.utils.ClickListener#clicked(com.badlogic.gdx.scenes.scene2d.InputEvent, float, float)
						 */
						@Override
						public void clicked(InputEvent event, float x, float y) {
							// TODO Auto-generated method stub
							map.setChoosenLevel(levelNumber);
							game.setScreen(game.screen.getNextScreen(State.ADVENTURE));
							super.clicked(event, x, y);
						}
						
					});
					if(levels.get(levelNo - 1).isLocked()){
						level.setLockRegion(Assets.lock);
						level.setTouchable(Touchable.disabled);
					}
					container.add(level).size(Utils.WIDTH_EXTRA_SMALL() , Utils.WIDTH_EXTRA_SMALL()).pad(0, 10, Utils.HEIGHT_SMALL(), 10);
				}
				container.row();
			}
			pageScroll.addPage(container).expand().fill();
		}
		
		
		pageScroll.setScrollingDisabled(false, true);
		background.add(pageScroll).expand().fill();
		background.row();
		background.add(indicatorBox)
		.size(stage.getWidth() * 0.5f , Utils.HEIGHT_SMALL() * 0.5f)
		.padBottom(10);
		stage.addActor(background);
		stage.addActor(backButton);
		
		//pageScroll.scrollToPage(map.getCurrentPage() - 1);
		
	}

	@Override
	public void disposeSub() {
		// TODO Auto-generated method stub
		pageScroll.clear();
		background.clear();
	}

	@Override
	public void hideSub() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeIndicator(int index) {
		// TODO Auto-generated method stub
		indicatorBox.setCurrent_indicator(index);
	}

	@Override
	public void BackKeyPressed() {
		// TODO Auto-generated method stub
		game.setScreen(game.screen.getNextScreen(State.MAP));
	}
}
