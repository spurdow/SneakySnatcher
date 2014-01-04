package com.teampora.scrollpanes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.tablelayout.Cell;

public class SneakyScrollPane extends ScrollPane {

	
	public interface Indicator{
		void changeIndicator(int index);
	}
	
	private Indicator indicator;
	
	private boolean wasPanDragFling = false;
	
	private Table content;
	
	private float spacing;

	
	public SneakyScrollPane(){
		super(null);
		setUp();
	}
	
	public SneakyScrollPane(Skin skin) {
		super(null, skin);
		// TODO Auto-generated constructor stub
		setUp();
	}
	
	private void setUp(){
		content = new Table();
		content.defaults().space(50);
		super.setWidget(content);
	}
	
	public Cell addPage(Actor page){
		return content.add(page);
	}
	
	public void addPages(Actor... pages){
		for(Actor page: pages){
			content.add(page);
		}
	}

	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);
		if(wasPanDragFling && !isDragging() && !isFlinging() && !isPanning()){
			wasPanDragFling = false;
			scrollToPage();
		}else{
			if(isPanning() || isDragging() || isFlinging()){
				wasPanDragFling = true;
			}
		}
	}
	
	
	
	
	@Override
	public void setWidget(Actor widget) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void setWidth(float width) {
		// TODO Auto-generated method stub
		super.setWidth(width);
		if(content != null){
			for(Cell cell : content.getCells()){
				cell.width(width);
			}
			content.invalidate();
		}
	}
	
	public void setPageSpacing(float space){
		if(content != null){
			content.defaults().space(space);
			for(Cell cell : content.getCells()){
				cell.space(space);
			}
			content.invalidate();
		}
	}

	protected void scrollToPage(){
		final float scrollX = getScrollX();
		final float maxX = getMaxX();
		
		if(scrollX >= maxX || scrollX <= 0 ) return;
		
		Array<Actor> pages = content.getChildren();
		float pageX  = 0;
		float pageWidth = 0;
		int index = 0;
		if(pages.size > 0){
			for(Actor a : pages){
				 pageX = a.getX();
				 pageWidth = a.getWidth();
				 /*
				  *  the pages scroll is greater than the users scroll
				  *  if it does it clamps the pageX see below code
				  */
				 
				 if (scrollX < (pageX + pageWidth / 2)) {
					 /*
					  * break to get the pageX 
					  * see below code the clamp shite!
					  */
					 if(indicator != null){
						 indicator.changeIndicator(index);
					 }
					 break;
				 }	
				 index++;
			}
			/*
			 *  clamps the scroll to where the greater width between scrollX[user scrolls] and page's half size + the startX of Page
			 */
			setScrollX(MathUtils.clamp(pageX , 0, maxX));
			
		}
	}
	
	public void scrollToPage(int page){
		Array<Actor> pages = content.getChildren();
		if(pages.size > 0 && page < pages.size){
			float pageX = pages.get(page).getX();
			setScrollX(pageX);
		}
		
	}
	
	public void setIndicatorListener(Indicator indicator){
		this.indicator = indicator;
	}

}
