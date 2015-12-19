# GameEngine
A 2D Java game engine I have been working on.

##Summary:
This is a little fun project I started on a whim while on a trip where I had no internet. First I just thought of it as a little fun thing to test how far I would get and to see my skills. But after the trip I just couldn't leave it, it was just so fun doing. And so here I am...

##The Idea:
The idea of the engine is to be a object oriented easy to use and easily extended game engine. Some of the things I want to achive with this engine is that almost all parts are interchangable or rewritable. This would mean that any part of the game engine e.g. the paint routine can be entierly replaced by just extending a class or inplementing an interface. In my opinion this is a really nice thing to be able to do.

##The Implementation:
Here I will discuss the inner workings of the game engine and how to use it.
####The `Game` class:
The entire game engine operates in two threads, one for the main game logic and one for painting. The game logic thread and main loop is located in the `Game` class. The main method creates a `Game` object and then calls the `run()` method where the main loop should be located. It is inside this `run()` method where the paint thread gets initialized and started. All setup of game object, controllers and other game logic related things for the initial part of the game should be put in the `Game()` constructor.
####The `GameObject` interface:
To allow for some flexability in the way that game objects are handled the base 'game object' is a interface. This allows the user to decide what properties the game object should have. For example, the user might want a collider/trigger somewhere in the scene but might not want the object to be seen, the user could do this by implementing the `Collidable` interface and just avoid implementing the `Paintable` interface. Note, the `Collidable` and `Paintable` interfaces are subinterfaces of game object. While handling all game object  allows for fexibility the downside is that some interfaces might define the same methods so that they can work even when they are not used together, this is normaly not a problem it's just not very pretty. Another downside is that no standard procedures for simple basic behaviour can be defined, this means for every new game object all behaviour must be modelled from scratch. To facilitate this a number of standard abstract classes where created that model some base behaviour so that this task is not left up to the user. One example if such a class is the `Sprite` class that models standard behaviour for the `Paintable` and `Movable` game object interfaces, another example is the `BasicGameObject` class whitch models a barebones GameObject.
###The `GameObjectHandler` class:
The `GameObjectHandler` class is responsible for storing all game objects in a scene. This means that all game object created must be added to the game object handler for anything to happen to them at all. The exact way to add a game object to the game object handler will probably change quite a lot but for now the way you do it is by adding the game object to the game object handler via the `<T extends GameObject> void addGameObject(T)` method. This allows other subsystems to query the game object handeler for different types of game objects. The game object handeler makes use of an `IDHandler<GameObject>` object to keep track of all the game objects in an organised and easy way. The game object handler has methods for getting game objects of specific types via the `<T extends GameObject> void getAllGameObjectExtending(Class<T>)` method. E.g. a this call `gameObjectHandler.getAllGameObjectsExtending(Paintable.class)` would return a list of all `Paintables` that has been added to the `gameObjectHandler` object. It should be noted that this is an expensive method and should not be called very often, insted systems that want to get objects should keep a list of objects for themselves and only update their list when the `haveObjectsChanged()` returns true.
###The `Screen` and `ScreenManager`:
The paint loop and thread are located in the `Screen` class and the window that is displayed is provided by the `ScreenManager` utility class. The `ScreenManager` class has a set of static methods for creating and managing a single doubble buffered `javax.swing.JFrame`. The `Screen` class uses a `Painter` object to paint all graphics to the graphics object provided by the `ScreenManager` and then update the doubble buffer in the `ScreenManager`. The `Screen` also allows for a debug overlay with the `setDebugEnabled(boolean)` method.
###The `Painter` and `Paintable` class and interface:
The `Painter` is an abstract class that defines a base behavour for painting game objects. This is done with a list of `Paintables` whose bounds are intersected to determine whether or not to draw the `Paintable` or not. When overriding the `Painter.paint(Graphics2D)` method you should only update the `paintables` list before calling the super method. The `Camera` class in a standard class that has this basic behviour and supports movement.
##To be continued...
#####The `Updater` and `UpdateListener` class and interface:
#####The `Input` class:
#####The `IOHandler` class:
#####The `LoadRequest`, `SaveRequest` and `LoadResult` classes:
#####The `Loader` interface and DefaultLoaders:
#####The `Saver` interface and DefaultSavers:
#####The `AudioEngine` and `AudioSource` class and interface:
#####The `UI`, `UIElement` and `UIContainer` classes:'
#####The `IDHandler` class:
#####The `IDHandlerDebugFrame` class:
###Not implemented yet:
* The EventSystem
* Networking
* Scenes
* GameStates
* Controllers
