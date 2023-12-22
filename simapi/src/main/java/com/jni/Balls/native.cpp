#include "com_jni_Balls_Balls.h"
#include <iostream>
#include <thread>
#include <vector>
#include "box2d/box2d.h"

using namespace std;

#define OBJ_COUNT 1000

#define R 10

b2Vec2 gravity(0.0f,10.0f);

b2World world(gravity);

b2Body* walls[3];

bool started = false;

vector<unique_ptr<b2FixtureDef>> fixture_defs;
vector<unique_ptr<b2BodyDef>> body_defs;
vector<unique_ptr<b2CircleShape>> circle_shapes;
vector<unique_ptr<b2PolygonShape>> polygon_shapes;

b2Body * bodies[OBJ_COUNT];

thread * t;

void loop() {
  float timeStep = 1.0f / 60.0f;
  int velocityIterations = 6;
  int positionIterations = 2;
  while(true){
    world.Step(timeStep,velocityIterations,positionIterations);
  }
}

b2Vec2 mousePos(0,0);

JNIEXPORT void JNICALL Java_com_jni_Balls_Balls_start
  (JNIEnv *, jobject) {
    if(started){
      return;
    }
    started = true;

    unique_ptr<b2BodyDef> wallbodydef = make_unique<b2BodyDef>();
    wallbodydef.get()->position.Set(500.0f, 1000.0f);
    walls[0] = world.CreateBody(wallbodydef.get());
    unique_ptr<b2PolygonShape> wallbox = make_unique<b2PolygonShape>();
    wallbox.get()->SetAsBox(500.0f, 50.0f);
    walls[0]->CreateFixture(wallbox.get(), 0.0f);
    body_defs.push_back(move(wallbodydef));
    polygon_shapes.push_back(move(wallbox));

    
    unique_ptr<b2BodyDef> wallbodydef2 = make_unique<b2BodyDef>();
    wallbodydef2.get()->position.Set(1000.0f, 500.0f);
    walls[1] = world.CreateBody(wallbodydef2.get());
    unique_ptr<b2PolygonShape> wallbox2 = make_unique<b2PolygonShape>();
    wallbox2.get()->SetAsBox(50.0f, 500.0f);
    walls[1]->CreateFixture(wallbox2.get(), 0.0f);
    body_defs.push_back(move(wallbodydef2));
    polygon_shapes.push_back(move(wallbox2));


    unique_ptr<b2BodyDef> wallbodydef3 = make_unique<b2BodyDef>();
    wallbodydef3.get()->position.Set(0.0f, 500.0f);
    walls[2] = world.CreateBody(wallbodydef3.get());
    unique_ptr<b2PolygonShape> wallbox3 = make_unique<b2PolygonShape>();
    wallbox3.get()->SetAsBox(50.0f, 500.0f);
    walls[2]->CreateFixture(wallbox3.get(), 0.0f);
    body_defs.push_back(move(wallbodydef3));
    polygon_shapes.push_back(move(wallbox3));

    
    for(int i=0;i<OBJ_COUNT;i++){
        unique_ptr<b2BodyDef> bodyDef = make_unique<b2BodyDef>();
        bodyDef.get()->type = b2_dynamicBody;
        bodyDef.get()->position.Set(500.0f + (rand() % 10), 500.0f - i*(R+5));
        bodies[i] = world.CreateBody(bodyDef.get());
        unique_ptr<b2CircleShape> box = make_unique<b2CircleShape>();
        box.get()->m_radius = R;
        unique_ptr<b2FixtureDef> fixtureDef = make_unique<b2FixtureDef>();
        fixtureDef.get()->shape = box.get();
        fixtureDef.get()->density = 1.0f;
        fixtureDef.get()->friction = 0.3f;
        bodies[i]->CreateFixture(fixtureDef.get());
        body_defs.push_back(move(bodyDef));
        circle_shapes.push_back(move(box));
        fixture_defs.push_back(move(fixtureDef));
    }

    // your mouse will move this object
    circle_shapes[0].get()->m_radius = 20;
    bodies[0]->SetGravityScale(0);

    t = new thread(loop);
  }


JNIEXPORT jobjectArray JNICALL Java_com_jni_Balls_Balls_ballPositions
  (JNIEnv * env, jobject,jclass posClass) {
    jobjectArray coords = env->NewObjectArray(OBJ_COUNT,posClass,nullptr);
    for(int i=0;i<OBJ_COUNT;i++){
      jobject obj = env->AllocObject(posClass);
      env->SetFloatField(obj,env->GetFieldID(posClass,"x","F"),bodies[i]->GetPosition().x);
      env->SetFloatField(obj,env->GetFieldID(posClass,"y","F"),bodies[i]->GetPosition().y);
      env->SetObjectArrayElement(coords,i,obj);
    }
    return coords;
}

JNIEXPORT void JNICALL Java_com_jni_Balls_Balls_mousePos(JNIEnv *, jobject, jint x, jint y) {
  mousePos.Set(x,y);
  
  // move towards mouse
  const b2Vec2 dir((x-bodies[0]->GetPosition().x)*1000000,(y-bodies[0]->GetPosition().y)*1000000);
  bodies[0]->SetLinearVelocity(dir);
}



