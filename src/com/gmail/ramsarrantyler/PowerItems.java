package com.gmail.ramsarrantyler; //First line! :D

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.event.EventHandler;

@SuppressWarnings("deprecation")
public final class PowerItems extends JavaPlugin {
	final HashMap<Player, ItemStack[]> invsave = new HashMap<Player, ItemStack[]>();
	final HashMap<Player, ItemStack[]> armorsave = new HashMap<Player, ItemStack[]>();
	@Override
	public void onEnable(){ //Upon enable of the plugin, do something.
	       getLogger().info("PowerItems has been successfully enabled! :)");//Code goes here.
	       getLogger().info("Remember, if updating versions delete the previous configuration.");
	       getConfig().options().copyDefaults(true);
	       saveConfig();
	       new PowerItemsListener(this);
	       this.getServer().getPluginManager().registerEvents(new PowerItemsListener(this), this);
	       getCommand("coord").setExecutor(new PowerItemsCommandExecutor(this));
	       getCommand("pireload").setExecutor(new PowerItemsCommandExecutor(this));
	       getCommand("fly").setExecutor(new PowerItemsCommandExecutor(this));
	       getCommand("doublejump").setExecutor(new PowerItemsCommandExecutor(this));
	       getCommand("mod").setExecutor(new PowerItemsCommandExecutor(this));
	       getCommand("ender").setExecutor(new PowerItemsCommandExecutor(this));
	}
	
    public static Inventory directory = Bukkit.createInventory(null, 9, "Directory");
    static {
		
		ItemStack compass = new ItemStack(Material.COMPASS, 1);
		ItemMeta compass_meta = compass.getItemMeta();
		ArrayList<String> compass_lore = new ArrayList<String>();
		compass_lore.add("Sends you back to spawn."); //Set lore
		compass_meta.setLore(compass_lore);
		compass_meta.setDisplayName("Spawn"); //Set name
		compass.setItemMeta(compass_meta);
		directory.setItem(0, compass);
		
		ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta pick_meta = pick.getItemMeta();
		ArrayList<String> pick_lore = new ArrayList<String>();
		pick_lore.add("Nothing beats plain 'ol survival.");
		pick_meta.setLore(pick_lore);
		pick_meta.setDisplayName("Survival");
		pick.setItemMeta(pick_meta);
		directory.setItem(1, pick);
		
		ItemStack fire = new ItemStack(Material.FIRE, 1);
		ItemMeta fire_meta = fire.getItemMeta();
		ArrayList<String> fire_lore = new ArrayList<String>();
		fire_lore.add("Build anything you like, or view other's builds.");
		fire_meta.setLore(fire_lore);
		fire_meta.setDisplayName("Creative");
		fire.setItemMeta(fire_meta);
		directory.setItem(2, fire);
		
		ItemStack arrow = new ItemStack(Material.ARROW, 1);
		ItemMeta arrow_meta = arrow.getItemMeta();
		ArrayList<String> arrow_lore = new ArrayList<String>();
		arrow_lore.add("Hunger Games, Spleef, DTC, and more!");
		arrow_meta.setLore(arrow_lore);
		arrow_meta.setDisplayName("Games");
		arrow.setItemMeta(arrow_meta);
		directory.setItem(3, arrow);
		
		ItemStack iron = new ItemStack(Material.IRON_INGOT, 1);
		ItemMeta iron_meta = iron.getItemMeta();
		ArrayList<String> iron_lore = new ArrayList<String>();
		iron_lore.add("Stay tuned for more!");
		iron_meta.setLore(iron_lore);
		iron_meta.setDisplayName("Coming Soon!");
		iron.setItemMeta(iron_meta);
		directory.setItem(4,iron);
		}
    
    
	public class PowerItemsListener implements Listener {
		public PowerItems plugin;
		
		public PowerItemsListener(PowerItems plugin) {
			this.plugin = plugin;
		}
			@EventHandler
			public void onLogin(final PlayerJoinEvent evt) {
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					 
			        @Override
			        public void run() {
			            evt.getPlayer().sendMessage(ChatColor.AQUA + "[PowerItems] " + ChatColor.DARK_RED + "This server proudly runs Siioh's PowerItems plugin!");
			            Calendar cal = Calendar.getInstance();
			            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
			            if(cal.get(Calendar.MONTH) == 0 && dayOfMonth == 0) {
			            	evt.getPlayer().sendMessage(ChatColor.AQUA + "[PowerItems] " + ChatColor.DARK_RED + "We want to wish you a Happy New Year from the DoubleRealm!");
			            }
			        }
				}, 10L);
			}
			
			@EventHandler
	        public void onInventoryClick(InventoryClickEvent evt) {
				Player player = (Player) evt.getWhoClicked(); // The player that clicked the item
	        	ItemStack clicked = evt.getCurrentItem(); // The item that was clicked
	        	Inventory inventory = evt.getInventory(); // The inventory that was clicked in
	        	if (inventory.getName().equals(directory.getName())) {
	        		evt.setCancelled(true);// The inventory is our custom directory
	        		player.closeInventory(); // Closes the inventory
	        		if (clicked.getType() == Material.COMPASS) { // The item that the player clicked is a compass
	        		    int x1 = plugin.getConfig().getInt("Slot 1 X");
	        		    int y1 = plugin.getConfig().getInt("Slot 1 Y");
	        		    int z1 = plugin.getConfig().getInt("Slot 1 Z");
	        		    Location slot1 = new Location(player.getWorld(), x1, y1, z1);
	        		    player.teleport(slot1);
	        		}
	        		
	        		if(clicked.getType() == Material.DIAMOND_PICKAXE) {
	        			player.closeInventory();
	        			int x2 = plugin.getConfig().getInt("Slot 2 X");
	        			int y2 = plugin.getConfig().getInt("Slot 2 Y");
	        			int z2 = plugin.getConfig().getInt("Slot 2 Z");
	        			Location slot2 = new Location(player.getWorld(), x2, y2, z2);
	        			player.teleport(slot2);
	        		}
	        			
	        		if(clicked.getType() == Material.FIRE) {
	        			player.closeInventory();
	        			int x3 = plugin.getConfig().getInt("Slot 3 X");
	        			int y3 = plugin.getConfig().getInt("Slot 3 Y");
	        			int z3 = plugin.getConfig().getInt("Slot 3 Z");
	        			Location slot3 = new Location(player.getWorld(), x3, y3, z3);
	        			player.teleport(slot3);
	        		}
	        			
	        		if(clicked.getType() == Material.ARROW) {
	        			int x4 = plugin.getConfig().getInt("Slot 4 X");
	        			int y4 = plugin.getConfig().getInt("Slot 4 Y");
	        			int z4 = plugin.getConfig().getInt("Slot 4 Z");
	        			Location slot4 = new Location(player.getWorld(), x4, y4, z4);
	        			player.teleport(slot4);
	        		}
	        			
	        		if(clicked.getType() == Material.IRON_INGOT) {
	        			int x5 = plugin.getConfig().getInt("Slot 5 X");
	        			int y5 = plugin.getConfig().getInt("Slot 5 Y");
	        			int z5 = plugin.getConfig().getInt("Slot 5 Z");
	        			Location slot5 = new Location(player.getWorld(), x5, y5, z5);
	        			player.teleport(slot5);
	        		}
	        		player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
	        	}
	        }
	        
			@EventHandler
	        public void onPlayerChat(PlayerChatEvent evt){
	            Player player = evt.getPlayer();
	            String name = player.getName();
	        	if(name.equalsIgnoreCase("Siioh")){
	        		World world = player.getWorld();
	        		Location loc = player.getLocation();
	        		world.playEffect(loc, Effect.MOBSPAWNER_FLAMES, 0);
	        	}
	        }
			
			@EventHandler
			public void onSignChange(SignChangeEvent evt){
				if (evt.getBlock().getTypeId() == 68 || evt.getBlock().getTypeId() == 63){
					Sign cmd_sign = (Sign)(evt.getBlock().getState());
    				String[] lines = evt.getLines();
    				if(lines[0].equalsIgnoreCase("[Command]")){
    					Player player = evt.getPlayer();
    					if(lines[1] != null && !lines[1].isEmpty()){
    						evt.setLine(0, ChatColor.GREEN + "[Command]");
    						cmd_sign.update(true);
    					} else {
    						player.sendMessage("You need a command on the second line.");
    						evt.setLine(0, ChatColor.DARK_RED + "[Command]");
    						cmd_sign.update(true);
    					}
    				}
				}
			}
			@EventHandler
		    public void onPlayerInteract(PlayerInteractEvent evt){
				Player player = evt.getPlayer(); //Setting all the variables for the event, helps clean code.
				Inventory player_inventory = player.getInventory(); //Gets inventory of player.
				ItemStack in_hand = player.getItemInHand(); //In hand item.
				Action action = evt.getAction(); //Action within the event.
				World world = player.getWorld(); //World player is in.
				Location player_loc = player.getLocation(); //Location of the player.
				Vector velocity_toss = player_loc.getDirection().multiply(2); //Vector of projectile speeds.
				Location direction = player.getEyeLocation().toVector().add(player_loc.getDirection().multiply(2)).toLocation(world, player_loc.getYaw(), player_loc.getPitch());			
				if (in_hand.getTypeId() == Material.COMPASS.getId() && player.hasPermission("PowerItems.directory") && player.getWorld().getName() == "Spawn"){
					player.openInventory(directory);
				}
				
				if (in_hand.getTypeId() == Material.SKULL_ITEM.getId()){
					if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
						evt.setCancelled(true);
						Inventory end_chest = player.getEnderChest();
						player.openInventory(end_chest);
						player.setItemInHand(null);
					}
				}
				
				if(in_hand.getTypeId() == Material.BLAZE_POWDER.getId()){
					if(action.equals(Action.RIGHT_CLICK_AIR) ||action.equals(Action.RIGHT_CLICK_BLOCK)) {
		            	if(player.hasPermission("PowerItems.lightning")){
		            		if (Cooldowns.tryCooldown(player, "Blaze_Powder", 500)) { //Change only here to set Cooldown (In milliseconds)
		            			world.strikeLightning(player.getTargetBlock(null, 200).getLocation());
		            			if(player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SURVIVAL){
		            				if (in_hand.getAmount() == 1) {
		            					player.setItemInHand(null);
		            				}
		            				if (in_hand.getAmount() > 1){
		            					in_hand.setAmount(in_hand.getAmount()-1);
		                    		}
	            	        	}
		            		}
						}
					}
				}
				
		    	    if (in_hand.getTypeId() == Material.FIREBALL.getId()){//Spawn Fireball
		    	    	if(player.hasPermission("PowerItems.throwfireball")){
		    	    		ItemMeta fireball = player.getItemInHand().getItemMeta();
		    	    		if(fireball.hasDisplayName()) {
		    	    			if(fireball.getDisplayName().equalsIgnoreCase(plugin.getConfig().getString("Throwable Fire Charge"))) {
		    	    				if (Cooldowns.tryCooldown(player, "Fireball", 250)) { //Change only here to set Cooldown (In milliseconds)
		    	    					world.spawn(direction, Fireball.class);
		    	    					if(player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SURVIVAL){
		    	    						if(in_hand.getAmount() == 1){
		    	    							player.setItemInHand(null);
		    	    						}
		    	    						if(in_hand.getAmount() > 1){
		    	    								in_hand.setAmount(in_hand.getAmount()-1);
		    	    						}
		    	    					}
		    	    				}
		    	    			}
		    	    		}
		            	}
		    	    }
		    	    
		    		if (in_hand.getTypeId() == Material.TNT.getId()){
		    			if(player.hasPermission("PowerItems.throwtnt")){
					        TNTPrimed tnt = (TNTPrimed) world.spawn(direction, TNTPrimed.class);
					        tnt.setVelocity(velocity_toss);
					        if(player.getGameMode() == GameMode.ADVENTURE || player.getGameMode() == GameMode.SURVIVAL){
					        	if(in_hand.getAmount() == 1){
					        		player.setItemInHand(null);
					        	}
					        	if(in_hand.getAmount() > 1){
					                in_hand.setAmount(in_hand.getAmount()-1);
					        	}
					        }
		    			}

		    		}
		    		
		    		if(evt.getAction() == Action.RIGHT_CLICK_BLOCK){
		    			if (evt.getClickedBlock().getTypeId() == 68 || evt.getClickedBlock().getTypeId() == 63){
		    				Sign cmd_sign = (Sign)(evt.getClickedBlock().getState());
		    				String[] lines = cmd_sign.getLines();
		    				if(lines[0].equalsIgnoreCase(ChatColor.GREEN + "[Command]")){
		    					if(lines[1] != null && !lines[1].isEmpty()){
		    						plugin.getServer().dispatchCommand(player, lines[1]);
		    					} else {
		    						player.sendMessage("You need a command on the second line.");
		    					}
		    				}
		    			}
		    		}
		    		//if((evt.getMaterial() == Material.DIAMOND_HOE)) {
		    			//if(evt.getPlayer().hasPermission("PowerItems.special")){
		    				//String hoe_name = "[iChapz Hoe]";
		    				//String cooldown = " seconds left until you can use this again.";
		    				//if (Cooldowns.tryCooldown(player, "Wrath", 10000)){	
		    					//world.playSound(player_loc, Sound.EXPLODE, 10, 1);
		    					//String specialty = "iChapz has shown his wrath! Run for your life!";
		    					//Bukkit.broadcastMessage(ChatColor.GOLD + hoe_name + ChatColor.AQUA + specialty);
		    					//player.playEffect(player_loc, Effect.MOBSPAWNER_FLAMES, 0);
		    				//}
		    				//else{
		    					//player.sendMessage(ChatColor.GOLD + hoe_name + ChatColor.GRAY + " You have " + (Cooldowns.getCooldown(player, "Wrath") / 100.0) + cooldown);
		    				//}
		    			//}
		    		//}
		    		
		    		//if((in_hand == new ItemStack(Material.getMaterial(38)))){
		    			//player.playSound(player_loc, Sound.EXPLODE, 10, 1);
		    			//player.sendMessage(ChatColor.AQUA + "BOOM! (In honor of Bay's dream of 'Exploding Petals')");
		    			//world.playEffect(player_loc, Effect.MOBSPAWNER_FLAMES, 0);
		    			//world.playEffect(player_loc, Effect.ENDER_SIGNAL, 0);
		    		//}
		    		
		    		if(evt.getMaterial() == Material.WOOD_SPADE) {
		    			ItemMeta shovel = in_hand.getItemMeta();
		                if(shovel.hasDisplayName()) {
		                	if(shovel.getDisplayName().equalsIgnoreCase(plugin.getConfig().getString("Sniper Bow"))) { //Use ¤6 for gold color.
		            	        if(player.hasPermission("PowerItems.sniper")) {    
		                		if(player_inventory.contains(Material.ARROW)) {
		            	            	if (Cooldowns.tryCooldown(player, "Sniper", 2000)) { //Change only here to set Cooldown (In milliseconds)
		            	            		world.spawnArrow(direction, velocity_toss, 10F, 0F);
					                        int current = in_hand.getDurability();
					                        int newdur = current + 1;
					                        in_hand.setDurability((short) newdur);
					                        if(current < 50) {
					                        	ArrayList<String> lore = new ArrayList<String>();
					                        	lore.add("Uses: " + current + "/50"); //Use a second command to set another line.
					                        	lore.add("WARNING: The durability bar isn't accurate.");
					                        	lore.add("This item doesn't work with enchantments");
					                        	shovel.setLore(lore);
					                        	in_hand.setItemMeta(shovel);
					                        }
					                        if(current == 50) {
					                    	    player.setItemInHand(null);
					                    	    player.playSound(player_loc, Sound.ITEM_BREAK, 10, 1);
					                        }
		            	            	} else {
		            	            		String shovel_name = "[Sniper Bow]";
		            	            		String cooldown = " seconds left until you can shoot again.";
		            	            	    player.sendMessage(ChatColor.GOLD + shovel_name + ChatColor.GRAY + " You have " + (Cooldowns.getCooldown(player, "Sniper") / 1000.0) + cooldown);
		            	            	}
		            	            }
		            	        }
		                	}
		                }
		                if(shovel.hasDisplayName()) {
		                	if(shovel.getDisplayName().equalsIgnoreCase(plugin.getConfig().getString("Machine Bow"))) {
		                		if(player.hasPermission(("PowerItems.machine"))){
	            	            if(player_inventory.contains(Material.ARROW)) {
	            	            	if (Cooldowns.tryCooldown(player, "Machine", 200)) { //Change only here to set Cooldown (In milliseconds)
	            	            		world.spawnArrow(direction, velocity_toss, 5F, 4F);
	            	            		player.playSound(player_loc, Sound.EXPLODE, 10, 1);
	            	            		ItemStack item = player.getItemInHand();
				                        int current = item.getDurability();
				                        int newdur = current + 1;
				                        item.setDurability((short) newdur);
				                        if(current < 100) {
				                        	ArrayList<String> lore = new ArrayList<String>();
				                        	lore.add("Uses: " + current + "/100"); //Use a second command to set another line.
				                        	lore.add("WARNING: The durability bar isn't accurate.");
				                        	lore.add("This item doesn't work with enchantments.");
				                        	shovel.setLore(lore);
				                        	item.setItemMeta(shovel);
				                        }
				                        if(current == 100) {
				                    	    player.setItemInHand(null);
				                    	    player.playSound(player_loc, Sound.ITEM_BREAK, 10, 1);
				                        }
	            	            	}
	            	            }
	            	            }
		                	}
		                }
		    		}
				}
			
			@EventHandler
			public void EntityShootBow(EntityShootBowEvent evt){
				Entity entity = evt.getEntity();
				if(entity instanceof Player) {
					LivingEntity entity_alive = evt.getEntity();
				    Player player = (Player) entity_alive;
				    Inventory player_inventory = player.getInventory();
				    ItemStack in_hand = player.getItemInHand();
		            ItemMeta shovel = in_hand.getItemMeta();
		            Vector velocity_arrow = evt.getProjectile().getVelocity();
		            Vector velocity_toss = player.getLocation().getDirection().multiply(2);
		            Location player_loc = player.getLocation();
		            World world = player.getWorld();
		            Float speed = evt.getForce() * 2;
		            Location direction = player.getEyeLocation().toVector().add(player_loc.getDirection().multiply(2)).toLocation(world, player_loc.getYaw(), player_loc.getPitch());
				    if(shovel.hasDisplayName()) {
	                	if(shovel.getDisplayName().equalsIgnoreCase(plugin.getConfig().getString("Wither Bow"))) {
	                		if(player.hasPermission("PowerItems.wither")){
	            	            if(player_inventory.contains(Material.ARROW)) {
	            	            	evt.setCancelled(true);
	            	            	player.updateInventory();
	            	            	if (Cooldowns.tryCooldown(player, "Wither", 350)) { //Change only here to set Cooldown (In milliseconds)
	            	            		player.launchProjectile(WitherSkull.class).setVelocity(velocity_arrow);
		            	            	player.playSound(player_loc, Sound.SHOOT_ARROW, 10, 1);
				                        int current = in_hand.getDurability();
				                        int newdur = current + 1;
				                        in_hand.setDurability((short) newdur);
				                        if(newdur < 50) {
				                        	ArrayList<String> lore = new ArrayList<String>();
				                        	lore.add("Uses: " + newdur + "/50"); //Use a second command to set another line.
				                        	lore.add("WARNING: The durability bar isn't accurate.");
				                        	shovel.setLore(lore);
				                        	in_hand.setItemMeta(shovel);
				                        }
				                        if(newdur == 50) {
				                    	    player.setItemInHand(null);
				                    	    player.playSound(player_loc, Sound.ITEM_BREAK, 10, 1);
				                        }
	            	            	}
	            	            }
	                		}
	                	}
				    
	                	if(shovel.getDisplayName().equalsIgnoreCase(plugin.getConfig().getString("TNT Bow"))) {
	                		if(player.hasPermission("PowerItems.tntbow")){
	                		evt.setCancelled(true);
	                		player.updateInventory();
	                		if(player_inventory.contains(Material.ARROW) && player_inventory.contains(Material.TNT)) {
	                			player.playSound(player_loc, Sound.SHOOT_ARROW, 10, 1);
	                			TNTPrimed tnt = (TNTPrimed) world.spawn(direction, TNTPrimed.class);
	                			tnt.setVelocity(velocity_toss);
	                			ItemStack item = player.getItemInHand();
			                    int current = item.getDurability();
			                    if(current < 100) {
			                        ArrayList<String> lore = new ArrayList<String>();
			                        lore.add("Uses: " + current + "/100"); //Use a second command to set another line.
			                        lore.add("WARNING: The durability bar is for reload time.");
			                        shovel.setLore(lore);
			                        item.setItemMeta(shovel);
			                    }
			                    if(current == 100) {
			                    	player.setItemInHand(null);
			                    	player.playSound(player_loc, Sound.ITEM_BREAK, 10, 1);
			                    }
	                		}
	                		}
	                	}
	                	
	                	if(shovel.getDisplayName().equalsIgnoreCase(plugin.getConfig().getString("Shot Bow"))) {
	                		if(player.hasPermission("PowerItems.shotbow")){
	            	            if(player_inventory.contains(Material.ARROW)) {
	            	            	evt.setCancelled(true);
	            	            	player.playSound(player_loc, Sound.SHOOT_ARROW, 10, 1);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 0F);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 3F);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 6F);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 9F);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 12F);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 15F);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 18F);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 21F);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 24F);
	            	            	world.spawnArrow(direction,velocity_arrow, speed, 27F);
	            	            	ItemStack item = player.getItemInHand();
				                    int current = item.getDurability();
				                    int newdur = current + 1;
				                    item.setDurability((short) newdur);
				                    if(newdur < 100) {
				                        ArrayList<String> lore = new ArrayList<String>();
				                        lore.add("Uses: " + newdur + "/100"); //Use a second command to set another line.
				                        lore.add("WARNING: The durability bar isn't accurate.");
				                        shovel.setLore(lore);
				                        item.setItemMeta(shovel);
				                    }
				                    if(newdur == 100) {
				                    	player.setItemInHand(null);
				                    	player.playSound(player_loc, Sound.ITEM_BREAK, 10, 1);
				                    }
	            	            }
	                		}
	                	}
				    }
				}
			}
			
			@EventHandler
			public void onWorldChange(PlayerChangedWorldEvent evt){
				Player player = evt.getPlayer();
				World world = player.getWorld();
				String world_name = world.getName();
				if(world_name.equalsIgnoreCase("hub")){
					PlayerInventory inv = player.getInventory();
					player.sendMessage(ChatColor.AQUA + "[Games] " + ChatColor.WHITE + "You can join the MobArena by typing " + ChatColor.RED + "'/ma join'" + ChatColor.WHITE + ".");
					inv.clear();
                	inv.setHelmet(null);
                	inv.setChestplate(null);
                	inv.setLeggings(null);
                	inv.setBoots(null);
                	player.updateInventory();
					if(player.hasPermission("skywars.forcestart")){
						player.sendMessage(ChatColor.AQUA + "[Games] " + ChatColor.WHITE + "As a helper/admin, you can start SkyWars instantly using " + ChatColor.RED + "'/sw forcestart'" + ChatColor.WHITE + ".");
					}
				}
			}
			
			@EventHandler
			public void onPlayerMove(PlayerMoveEvent evt){
				Player player = evt.getPlayer();
				Location player_loc = player.getLocation();
				Location block = player.getLocation();
				Vector velocity = player_loc.getDirection().multiply(0.0).setY(1.3);
				int blockId = block.getWorld().getBlockTypeIdAt(block);
				if (blockId == 147){
					player.setVelocity(velocity);
					player.playSound(player_loc, Sound.FIREWORK_LAUNCH, 10, 0);
					PermissionAttachment attachment = player.addAttachment(plugin);
					attachment.setPermission("PowerItems.nofall", true);
				}
				block.setY(block.getY()-1);
				blockId = block.getWorld().getBlockTypeIdAt(block);
				if (!(blockId == 0)){
					if("true".equalsIgnoreCase(plugin.getConfig().getString("Double Jump"))) {
						if (player.hasPermission("PowerItems.doublejump")) {
							player.setAllowFlight(true);
							PermissionAttachment attachment = player.addAttachment(plugin);
							attachment.setPermission("PowerItems.nofall", true);
						}
					}
				}
			}
			
			@EventHandler
			public void PlayerJump(PlayerToggleFlightEvent evt){
				if("true".equalsIgnoreCase(plugin.getConfig().getString("Double Jump"))) {
					Player player = evt.getPlayer();
					if(player.hasPermission("PowerItems.doublejump")){
						World world = player.getWorld();
						Location player_loc = player.getLocation();
						Location block0 = player.getLocation();
						Location block1 = player.getLocation();
						Location block2 = player.getLocation();
						Location block3 = player.getLocation();
						Location block4 = player.getLocation();
						evt.setCancelled(true);
						block0.setY(block0.getY() -2);
						int blockId_bottom = block0.getWorld().getBlockTypeIdAt(block0);
						block1.setX(block1.getX() +1);
						int blockId_side1 = block1.getWorld().getBlockTypeIdAt(block1);
						block2.setX(block2.getX() -1);
						int blockId_side2 = block2.getWorld().getBlockTypeIdAt(block2);
						block3.setZ(block3.getZ() +1);
						int blockId_side3 = block3.getWorld().getBlockTypeIdAt(block3);
						block4.setZ(block4.getZ() -1);
						int blockId_side4 = block4.getWorld().getBlockTypeIdAt(block4);
				
						Vector double_jump = player.getLocation().getDirection().multiply(0.4).setY(1.3); //Originally multiply 0.2, setY 1.1
						if(blockId_bottom !=0 || blockId_side1 !=0 || blockId_side2 !=0 || blockId_side3 !=0 || blockId_side4 !=0) {
							player.setVelocity(player.getVelocity().add(double_jump));
							world.playEffect(player_loc, Effect.SMOKE, 1);
							player.playSound(player_loc, Sound.FALL_SMALL, 10, 0);
							player.setAllowFlight(false);
						}
					}
				}
			}
			
			@EventHandler
			public void EntityDamageFall(EntityDamageEvent evt) {
				DamageCause cause = evt.getCause();
				if(cause == DamageCause.FALL) {
					if(evt.getEntity() instanceof Player) {
						Player player = (Player) evt.getEntity();
						if(player.hasPermission("PowerItems.nofall")) {
							evt.setCancelled(true);
							PermissionAttachment attachment = player.addAttachment(plugin);
							attachment.unsetPermission("PowerItems.nofall");
						}
					}
				}
			}
			@EventHandler
			public void EntityDamageByEntity(EntityDamageByEntityEvent evt) {
				Entity e = evt.getEntity();
				if(evt.getDamager() instanceof Arrow) {
				    ((LivingEntity) e).setNoDamageTicks(0);
			    }   
			}
		}
	public class PowerItemsCommandExecutor implements CommandExecutor {
		public PowerItems plugin;
		 
		public PowerItemsCommandExecutor(PowerItems plugin) {
			this.plugin = plugin;
		}
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){ //On a command do...
        if(cmd.getName().equalsIgnoreCase("coord")){ // If the player typed /coord then do the following...
        	if(sender instanceof Player) { // Check if this sender is a player.
                Player player = (Player) sender;
                int x = player.getTargetBlock(null, 100).getX();
                int y = player.getTargetBlock(null, 100).getY();
                int z = player.getTargetBlock(null, 100).getZ();
    		    Location block = player.getLocation();
    		    block.setX(x);
    		    block.setY(y);
    		    block.setZ(z);
    		    if(block.getWorld().getBlockTypeIdAt(block) == 0) {
    		    	player.sendMessage(ChatColor.RED + "That block seems to be just air or is too far out.");
    		    } else {
    		    	player.sendMessage(ChatColor.AQUA + "X=" + x + ChatColor.RED + " Y=" + y + ChatColor.GREEN + " Z=" + z);
    		    }
                return true;
        	}
        	if(!(sender instanceof Player)) {
        		sender.sendMessage("You must be a player to use this command.");
        		return true;
        	}
        }
        if(cmd.getName().equalsIgnoreCase("mod")) {
        	if(sender instanceof Player) {
        		Player player = (Player) sender;
        		PermissionAttachment attachment = player.addAttachment(plugin);
        		if(!(player.hasPermission("PowerItems.mod")) || invsave.isEmpty() || armorsave.isEmpty()){
        			PlayerInventory inv = player.getInventory();
        			invsave.put(player, player.getInventory().getContents());
        			armorsave.put(player, inv.getArmorContents());
        			inv.clear();
                	inv.setHelmet(null);
                	inv.setChestplate(null);
                	inv.setLeggings(null);
                	inv.setBoots(null);
                	player.setGameMode(GameMode.CREATIVE);
                	attachment.setPermission("PowerItems.mod", true);
                	if(player.hasPermission("PowerItems.doublejump")){
                		player.sendMessage(ChatColor.AQUA + "[PowerItems] " + ChatColor.DARK_RED + "Since you are using mod mode, doublejump was automatically set to off.");
                		attachment.setPermission("PowerItems.doublejump", false);
                		attachment.setPermission("PowerItems.previous_doublejump", true);
                	}
                	player.updateInventory();
                	player.sendMessage(ChatColor.AQUA + "[PowerItems] " + ChatColor.DARK_RED + "Mod mode activated!");
                	return true;
        		} else {
        		if(player.hasPermission("PowerItems.mod")){
        		    player.setGameMode(GameMode.SURVIVAL);
        		    player.getInventory().setContents(invsave.get(player));
        		    player.getInventory().setArmorContents(armorsave.get(player));
        		    attachment.setPermission("PowerItems.mod", false);
        		    if(player.hasPermission("PowerItems.previous_doublejump")){
        		    	player.sendMessage(ChatColor.AQUA + "[PowerItems] " + ChatColor.DARK_RED + "Before turning mod mode on, you seemingly had doublejump on, we'll turn it back on for you.");
        		    	attachment.setPermission("PowerItems.doublejump", true);
        		    	attachment.setPermission("PowerItems.previous_doublejump", false);
        		    }
        		    player.updateInventory();
        		    player.sendMessage(ChatColor.AQUA + "[PowerItems] " + ChatColor.DARK_RED + "Mod mode turned off!");
        		    return true;
        		}
        		}
        	}
        	if(!(sender instanceof Player)) {
        		sender.sendMessage("You must be a player to run this command.");
        		return true;
        	}
        }
        if(cmd.getName().equalsIgnoreCase("ender")) {
        	if(sender instanceof Player) {
        		Player player = (Player) sender;
        		Inventory end_chest = player.getEnderChest();
        		player.openInventory(end_chest);
        		return true;
        	}
        	if(!(sender instanceof Player)) {
        		sender.sendMessage("You must be a player to use this command!");
        		return true;
        	}
        }
        if(cmd.getName().equalsIgnoreCase("pireload")) { //Reload the config files.
        	if(sender instanceof Player) {
        		sender.sendMessage(ChatColor.DARK_RED + "[PowerItems]" + ChatColor.AQUA + " You're a player, you can't do this!");
        		reloadConfig();
        		sender.sendMessage(ChatColor.DARK_RED + "[PowerItems]" + ChatColor.AQUA + " Reloaded successfully anyway.");
        		return true;
        	}
        	if(!(sender instanceof Player)) {
        		sender.sendMessage("Reloading.");
        		reloadConfig();
        		sender.sendMessage("Reload successful");
        		return true;
        	}
        }
        if(cmd.getName().equalsIgnoreCase("fly")) {
        	if(sender instanceof Player) {
        		Player player = (Player) sender;
        		boolean maybe = player.getAllowFlight();
        		if(maybe == true) {
        			player.setAllowFlight(false);
        			player.sendMessage("Flight was set to off");
        		}
        		if(maybe == false) {
        			player.setAllowFlight(true);
        			player.sendMessage("Flight was set to on");
        		}
        		return true;
        	}
        }
        if(!(sender instanceof Player)) {
        	sender.sendMessage("You must be a player to use this command.");
        	return true;
        }
        if(cmd.getName().equalsIgnoreCase("doublejump")) {
        	if("true".equalsIgnoreCase(plugin.getConfig().getString("Double Jump"))) {
        		if(sender instanceof Player) {
        			Player player = (Player) sender;
        			PermissionAttachment attachment = player.addAttachment(plugin);
        			if(player.hasPermission("PowerItems.doublejump")) {
        				attachment.setPermission("PowerItems.doublejump", false);
        				player.sendMessage("Double jump has been disabled.");
        			} else {
        				if(!(player.hasPermission("PowerItems.doublejump"))) {
        					attachment.setPermission("PowerItems.doublejump", true);
        					player.sendMessage("Double jump has been enabled.");
        				}
        			} return true;
        		}
        	if(!(sender instanceof Player)) {
        		sender.sendMessage("You must be a player to use this command.");
        		return true;
        	}
        	} else {
                if(!("true".equalsIgnoreCase(plugin.getConfig().getString("Double Jump")))) {
                	sender.sendMessage("Double jumping isn't on (Can be changed in the PowerItems config file).");
                	return true;
                }
        	}
        }
        return false;
    }
}
	
	@Override
    public void onDisable(){ //Upon disable of the plugin, do something.
 	   getLogger().info("PowerItems has been disabled/turned off."); //Code goes here.
	}
}